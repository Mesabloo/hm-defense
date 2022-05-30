package fr.mesabloo.heavymachdefense.screens

import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Json
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.*
import fr.mesabloo.heavymachdefense.listeners.stage.*
import fr.mesabloo.heavymachdefense.managers.animationManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.timers.cellMiningTimer
import fr.mesabloo.heavymachdefense.ui.stage.*
import fr.mesabloo.heavymachdefense.ui.stage.buttons.BaseUpgradeButton
import fr.mesabloo.heavymachdefense.ui.stage.buttons.BuildMachMenuButton
import fr.mesabloo.heavymachdefense.ui.stage.buttons.MenuButton
import fr.mesabloo.heavymachdefense.ui.stage.buttons.SpecialAttackMenuButton
import fr.mesabloo.heavymachdefense.ui.stage.dialog.SystemMenu
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.actors.setScrollFocus
import ktx.json.fromJson
import ktx.json.setSerializer
import kotlin.math.pow

class StageScreen(game: MainGame, private val level: Int, private val save: GameSave, isLoading: Boolean = false) :
    AbstractScreen(game, isLoading) {
    private companion object {
        const val TEMPORARY_CELL_UPGRADE_RATIO = 0.6f
    }

    private val json = Json()
    private val upgrades: Upgrades

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

    private lateinit var systemMenu: SystemMenu

    private var backgroundMusicVolume: Float = 1.0f
    private var effectsVolume: Float = 1.0f

    init {
        this.json.setSerializer(BaseCannonUpgradeSerializer)
        this.json.setSerializer(BaseDefenseUpgradeSerializer)
        this.json.setSerializer(BuildTimeUpgradeSerializer)
        this.json.setSerializer(CrResearchUpgradeSerializer)
        this.json.setSerializer(CellResearchUpgradeSerializer)
        this.json.setSerializer(CellStorageUpgradeSerializer)
        this.json.setSerializer(UpgradesJsonSerializer)

        this.upgrades = this.json.fromJson(Gdx.files.internal("data/upgrades.json"))

        this.maxCells = this.upgrades.cell_storage[this.save.mainUpgrades[UpgradeKind.CELL_STORAGE]?.minus(1)
            ?: 0].storage * 2f.pow(this.temporaryCellUpgradesCount.toFloat()).toLong()
        this.temporaryCellUpgradeCost = (this.maxCells * TEMPORARY_CELL_UPGRADE_RATIO).toLong()
        this.currentCells = this.maxCells * 2 / 5
    }

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        this.systemMenu = SystemMenu(this::backgroundMusicVolume, this::effectsVolume)

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
        this.background.addActor(CellTemporaryUpgrade(this::currentCells, this::temporaryCellUpgradeCost, this::temporaryCellUpgradesCount).also {
            it.setPosition(630f, 972f)
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

            it.addListener(ShowSystemMenu(this.systemMenu, this.ui))
        })
        controlsGroup.addActor(BaseUpgradeButton().also {
            it.setPosition(587f, 22f + 512f)
            it.addListener(ShowUpgradeMenu(this.menuTweenManager, controlsGroup, scrollpane, this::upgradeMenuShown))
        })
        controlsGroup.addActor(Title(StageAssetsManager.UI.TitleKind.BUILD_MACH).also {
            this.title = it

            it.setPosition(UI_WIDTH / 2f - it.width / 2f + 3f, 130f + 512f)
        })

        controlsGroup.addActor(BuildMachMenuButton().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f - 90f, 47f + 512f)
            it.addListener(RemoveClickIfUpgradeMenuShown(this::upgradeMenuShown))
            it.addListener(ResetAnimationsForOthers(controlsGroup, it))
            it.addListener(PlayAnimation(it))

            animationManager.setCurrentKeyframe(it.animationId, 7)
        })
        controlsGroup.addActor(SpecialAttackMenuButton().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f + 2f, 47f + 512f)
            it.addListener(RemoveClickIfUpgradeMenuShown(this::upgradeMenuShown))
            it.addListener(ResetAnimationsForOthers(controlsGroup, it))
            it.addListener(PlayAnimation(it))

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

        this.background.addActor(Radar(scrollpane).also {
            it.setPosition(22f, 698f)
        })

        this.terrain.setScrollFocus(true)
    }

    override fun render(delta: Float) {
        this.menuTweenManager.update(delta)

        // update cell storage capacity
        this.maxCells =
            this.upgrades.cell_storage[this.save.mainUpgrades[UpgradeKind.CELL_STORAGE]?.minus(1)
                ?: 0].storage * 2f.pow(this.temporaryCellUpgradesCount.toFloat()).toLong()
        this.temporaryCellUpgradeCost = (this.maxCells * TEMPORARY_CELL_UPGRADE_RATIO).toLong()

        super.render(delta)
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

        animationManager.dispose()
        stageAssetsManager.dispose()

        cellMiningTimer.clear()
    }
}