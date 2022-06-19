package fr.mesabloo.heavymachdefense.screens

import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.*
import fr.mesabloo.heavymachdefense.entities.createBases
import fr.mesabloo.heavymachdefense.entities.createTerrainBody
import fr.mesabloo.heavymachdefense.data.Specials
import fr.mesabloo.heavymachdefense.listeners.stage.*
import fr.mesabloo.heavymachdefense.managers.animationManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.timers.cellMiningTimer
import fr.mesabloo.heavymachdefense.ui.stage.*
import fr.mesabloo.heavymachdefense.ui.stage.buttons.*
import fr.mesabloo.heavymachdefense.ui.stage.dialog.SystemMenu
import fr.mesabloo.heavymachdefense.ui.stage.game.AllyBase
import fr.mesabloo.heavymachdefense.ui.stage.slots.MachineBuildSlot
import fr.mesabloo.heavymachdefense.ui.stage.slots.SpecialBuildSlot
import fr.mesabloo.heavymachdefense.ui.stage.slots.TurretBuildSlot
import fr.mesabloo.heavymachdefense.world.GameWorld
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ktx.actors.alpha
import ktx.actors.setScrollFocus
import kotlin.math.pow
import kotlin.properties.Delegates

class StageScreen(
    game: MainGame,
    private val level: Int,
    val save: GameSave,
    val saveIndex: Int,
    isLoading: Boolean = false
) :
    AbstractScreen(game, isLoading) {
    private companion object {
        const val TEMPORARY_CELL_UPGRADE_RATIO = 0.6f
        const val SLOT_MENU_WIDTH = 128f
        const val SLOT_MENU_HEIGHT = 710f
    }

    private lateinit var buildQueue: BuildQueue
    private lateinit var allyBase: AllyBase
    private lateinit var gameWorld: GameWorld

    private val upgrades: Upgrades = Json.decodeFromString(Gdx.files.internal("data/upgrades.json").readString())
    private val builds: Builds = Json {
        allowStructuredMapKeys = true // this is because we store 'Pair's as 'HashMap' keys
    }.decodeFromString(Gdx.files.internal("data/build-info.json").readString())
    private val specials: Specials = Json.decodeFromString(Gdx.files.internal("data/special-info.json").readString())

    private var upgradeMenuShown: Boolean = false

    private lateinit var title: Title
    private lateinit var terrain: Terrain

    private var playerLife: Long = 100L
    private var enemyLife: Long = 100L

    private val menuTweenManager = TweenManager()

    private var maxCells: Long
    private var currentCells: Long
    private var temporaryCellUpgradesCount: Long = 0
    private var temporaryCellUpgradeCost: Long

    private var cellResearchMultiplier: Float
    private var crResearchMultiplier: Float
    private var buildTimeMultiplier: Float

    private var baseDefenseLevel by Delegates.observable(1) { _, old, new ->
        if (!this.isLoading && old != new) {
            this.allyBase.defLevel = new
        }
    }
    private var baseAttackLevel by Delegates.observable(1) { _, old, new ->
        if (!this.isLoading && old != new) {
            this.allyBase.atkLevel = new
        }
    }

    private lateinit var systemMenu: SystemMenu

    private var backgroundMusicVolume: Float = 1.0f
    private var effectsVolume: Float = 1.0f

    private lateinit var upgradeEquipButton: UpgradeEquipment

    private var currentMenuKind by Delegates.observable(StageAssetsManager.UI.TitleKind.BUILD_MACH) { _, old, new ->
        if (old != new) {
            this.upgradeEquipButton.kind = new
            this.title.kind = new
        }
    }

    private lateinit var machineSlots: Group
    private lateinit var specialSlots: Group

    init {
        this.maxCells = this.upgrades.cell_storage[this.save.mainUpgrades[UpgradeKind.CELL_STORAGE]?.minus(1)
            ?: 0].storage * 2f.pow(this.temporaryCellUpgradesCount.toFloat()).toLong()
        this.temporaryCellUpgradeCost = (this.maxCells * TEMPORARY_CELL_UPGRADE_RATIO).toLong()
        this.currentCells = this.maxCells * 2 / 5

        this.cellResearchMultiplier =
            this.upgrades.cell_research[this.save.mainUpgrades[UpgradeKind.CELL_RESEARCH]?.minus(1) ?: 0].multiplier
        this.crResearchMultiplier =
            this.upgrades.cr_research[this.save.mainUpgrades[UpgradeKind.CR_RESEARCH]?.minus(1) ?: 0].multiplier
        this.buildTimeMultiplier =
            this.upgrades.build_time[this.save.mainUpgrades[UpgradeKind.BUILD_TIME]?.minus(1) ?: 0].multiplier
    }

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        this.systemMenu = SystemMenu(this::backgroundMusicVolume, this::effectsVolume, this)

        lateinit var scrollpane: ScrollPane
        this.background.addActor(ScrollPane(Terrain().also {
            this.terrain = it
        }).also {
            scrollpane = it

            val yOffset = 180f

            it.setBounds(128f, yOffset, 512f, UI_HEIGHT - yOffset)
            it.setSmoothScrolling(true)
            it.setScrollbarsVisible(false)
            it.setScrollingDisabled(true, false)
            it.setOverscroll(false, false)

            it.layout()
            it.scrollTo(0f, 0f, 512f, 1024f - yOffset)
            //it.layout()
        })

        this.background.addActor(HpGauges(this::playerLife, this::enemyLife).also {
            it.setPosition(128f, UI_HEIGHT - it.height - 3f)
        })
        this.background.addActor(BuildSlot().also {
            it.setPosition(0f, 0f)
        })
        this.background.addActor(MachSlot().also {
            it.setPosition(UI_WIDTH - it.width, 57f)
        })
        this.background.addActor(CellCounter(this::maxCells, this::currentCells).also {
            it.setPosition(652f, 995f)
        })
        this.background.addActor(
            CellTemporaryUpgrade(
                this::currentCells,
                this::temporaryCellUpgradeCost,
                this::temporaryCellUpgradesCount
            ).also {
                it.setPosition(630f, 972f)
            })

        this.background.addActor(BuildQueue(this::upgradeMenuShown, this.upgrades, this.save).also {
            this.buildQueue = it

            it.height = 508f
            it.width = 64f
            it.setPosition(22f, 692f - it.height)
        })

        this.background.addActor(Group().also {
            this.machineSlots = it

            var currentY = SLOT_MENU_HEIGHT - 76f
            for (slot in this.save.buildSlots) {
                it.addActor(when (slot) {
                    is MachineSlot -> MachineBuildSlot(
                        slot,
                        this.save,
                        this.builds,
                        this::currentCells,
                        this.buildQueue
                    )
                    is TurretSlot -> TurretBuildSlot(
                        slot,
                        this.save,
                        this.builds,
                        this::currentCells,
                        this.buildQueue
                    )
                    else -> TODO()
                }.also {
                    it.setPosition(40f, currentY)

                    it.addListener(
                        BuildMachineIfPossible(
                            it,
                            this::currentCells,
                            this.buildQueue,
                            this.builds,
                            this::upgradeMenuShown
                        )
                    )
                })

                currentY -= 102f
            }

            it.setPosition(640f, 966f - SLOT_MENU_HEIGHT)
            it.setSize(SLOT_MENU_WIDTH, SLOT_MENU_HEIGHT)

            it.alpha = 1f
            it.touchable = Touchable.enabled
        })
        this.background.addActor(Group().also {
            this.specialSlots = it

            var currentY = SLOT_MENU_HEIGHT - 76f
            for (slot in this.save.specialSlots) {
                it.addActor(when (slot) {
                    is SpecialSlot -> SpecialBuildSlot(slot, this.save, this.specials)
                    else -> TODO()
                }.also {
                    it.setPosition(40f, currentY)

                })

                currentY -= 102f
            }

            it.setPosition(640f, 966f - SLOT_MENU_HEIGHT)
            it.setSize(SLOT_MENU_WIDTH, SLOT_MENU_HEIGHT)

            it.alpha = 0f
            it.touchable = Touchable.disabled
        })

        this.background.addActor(UpgradeEquipment(this.currentMenuKind).also {
            this.upgradeEquipButton = it

            it.setPosition(650f, 180f)
        })

        val controlsGroup = Group()

        controlsGroup.addActor(Controls().also {
            it.setPosition(0f, 512f)
        })
        controlsGroup.addActor(StageNumber(level).also {
            it.setPosition(142f - it.width / 2f, 79f + 512f)
        })
        controlsGroup.addActor(Credits(save::credits).also {
            it.setPosition(176f - it.width, 30f + 512f)
        })
        controlsGroup.addActor(MenuButton().also {
            it.setPosition(587f, 89f + 512f)

            it.addListener(ShowSystemMenu(this.systemMenu, this.ui, this::upgradeMenuShown))
        })
        controlsGroup.addActor(BaseUpgradeButton().also {
            it.setPosition(587f, 22f + 512f)
            it.addListener(ShowUpgradeMenu(this.menuTweenManager, controlsGroup, scrollpane, this::upgradeMenuShown))
        })
        controlsGroup.addActor(Title(this.currentMenuKind).also {
            this.title = it

            it.setPosition(UI_WIDTH / 2f - it.width / 2f + 3f, 130f + 512f)
        })

        controlsGroup.addActor(BuildMachMenuButton().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f - 90f, 47f + 512f)
            it.addListener(RemoveClickIfUpgradeMenuShown(this::upgradeMenuShown))
            it.addListener(ResetAnimationsForOthers(controlsGroup, it))
            it.addListener(PlayAnimation(it))
            it.addListener(ShowBuildSideMenu(this::currentMenuKind, this.machineSlots, this.specialSlots))

            animationManager.setCurrentKeyframe(it.animationId, 7)
        })
        controlsGroup.addActor(SpecialAttackMenuButton().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f + 2f, 47f + 512f)
            it.addListener(RemoveClickIfUpgradeMenuShown(this::upgradeMenuShown))
            it.addListener(ResetAnimationsForOthers(controlsGroup, it))
            it.addListener(PlayAnimation(it))
            it.addListener(ShowSpecialSideMenu(this::currentMenuKind, this.machineSlots, this.specialSlots))

            animationManager.setCurrentKeyframe(it.animationId, 0)
        })
        controlsGroup.addActor(
            UpgradeMenu(
                this.save,
                this.upgrades,
                ShowUpgradeMenu(this.menuTweenManager, controlsGroup, scrollpane, this::upgradeMenuShown)
            ).also {
                it.setPosition(0f, -512f + 512f)
            })

        controlsGroup.setPosition(0f, -512f)

        this.background.addActor(controlsGroup)

        this.terrain.setScrollFocus(true)

        this.gameWorld = GameWorld(this.terrain)

        createTerrainBody(this.gameWorld)
        val (allyBase, _) = createBases(this.gameWorld, this.upgrades, this::save)
        this.allyBase = allyBase

        this.background.addActor(Radar(scrollpane).also {
            it.setPosition(22f, 698f)
        })
    }

    override fun render(delta: Float) {
        this.menuTweenManager.update(delta)

        // update cell storage capacity
        this.maxCells =
            this.upgrades.cell_storage[this.save.mainUpgrades[UpgradeKind.CELL_STORAGE]?.minus(1)
                ?: 0].storage * 2f.pow(this.temporaryCellUpgradesCount.toFloat()).toLong()
        this.temporaryCellUpgradeCost = (this.maxCells * TEMPORARY_CELL_UPGRADE_RATIO).toLong()

        this.cellResearchMultiplier =
            this.upgrades.cell_research[this.save.mainUpgrades[UpgradeKind.CELL_RESEARCH]?.minus(1) ?: 0].multiplier
        this.crResearchMultiplier =
            this.upgrades.cr_research[this.save.mainUpgrades[UpgradeKind.CR_RESEARCH]?.minus(1) ?: 0].multiplier
        this.buildTimeMultiplier =
            this.upgrades.build_time[this.save.mainUpgrades[UpgradeKind.BUILD_TIME]?.minus(1) ?: 0].multiplier

        this.baseDefenseLevel = this.save.mainUpgrades[UpgradeKind.BASE_DEFENSE] ?: 1
        this.baseAttackLevel = this.save.mainUpgrades[UpgradeKind.BASE_CANNON] ?: 1

        super.render(delta)

        if (!this.isLoading)
            this.gameWorld.render(delta)
    }

    override fun pause() {
        super.pause()

        cellMiningTimer.stop()
    }

    override fun resume() {
        super.resume()

        cellMiningTimer.start()
    }

    override fun dispose() {
        super.dispose()

        this.gameWorld.dispose()

        animationManager.dispose()
        stageAssetsManager.dispose()

        cellMiningTimer.clear()
    }
}