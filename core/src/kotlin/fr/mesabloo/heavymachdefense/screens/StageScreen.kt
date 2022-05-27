package fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.listeners.stage_selection.ResetAnimationsForOthers
import fr.mesabloo.heavymachdefense.listeners.stage_selection.PlayAnimation
import fr.mesabloo.heavymachdefense.managers.animationManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.ui.stage.*
import fr.mesabloo.heavymachdefense.ui.stage.buttons.BaseUpgradeButton
import fr.mesabloo.heavymachdefense.ui.stage.buttons.BuildMachMenuButton
import fr.mesabloo.heavymachdefense.ui.stage.buttons.MenuButton
import fr.mesabloo.heavymachdefense.ui.stage.buttons.SpecialAttackMenuButton
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.actors.setScrollFocus

class StageScreen(game: MainGame, private val level: Int, private val save: GameSave, isLoading: Boolean = false) :
    AbstractScreen(game, isLoading) {
    private lateinit var title: Title
    private lateinit var terrain: Terrain

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        lateinit var scrollpane: ScrollPane
        this.background.addActor(ScrollPane(Terrain().also {
            this.terrain = it
        }).also {
            scrollpane = it

            val yOffset = 180f

            it.setBounds(128f, yOffset, 512f, UI_HEIGHT - yOffset)
            //it.setSmoothScrolling(true)
            it.setScrollbarsVisible(false)
            it.setScrollingDisabled(true, false)
            it.setOverscroll(false, false)

            it.scrollTo(0f, 0f, 512f, 1024f - yOffset)
            //it.layout()
        })

        this.background.addActor(HpGauge().also {
            it.setPosition(128f, UI_HEIGHT - it.height - 3f)
        })
        this.background.addActor(BuildSlot().also {
            it.setPosition(0f, 0f)
        })
        this.background.addActor(MachSlot().also {
            it.setPosition(UI_WIDTH - it.width, 57f)
        })

        val controlsGroup = Group()

        controlsGroup.addActor(Controls().also {
            it.setPosition(0f, 0f)
        })
        controlsGroup.addActor(StageNumber(level).also {
            it.setPosition(142f - it.width / 2f, 79f)
        })
        controlsGroup.addActor(Credits(save::credits).also {
            it.setPosition(176f - it.width, 30f)
        })
        controlsGroup.addActor(MenuButton().also {
            it.setPosition(587f, 89f)
        })
        controlsGroup.addActor(BaseUpgradeButton().also {
            it.setPosition(587f, 22f)
        })
        controlsGroup.addActor(Title(StageAssetsManager.UI.TitleKind.BUILD_MACH).also {
            this.title = it

            it.setPosition(UI_WIDTH / 2f - it.width / 2f + 3f, 130f)
        })

        controlsGroup.addActor(BuildMachMenuButton().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f - 90f, 47f)
            it.addListener(PlayAnimation(it))
            it.addListener(ResetAnimationsForOthers(controlsGroup, it))

            animationManager.setCurrentKeyframe(it.animationId, 7)
        })
        controlsGroup.addActor(SpecialAttackMenuButton().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f + 2f, 47f)
            it.addListener(PlayAnimation(it))
            it.addListener(ResetAnimationsForOthers(controlsGroup, it))

            animationManager.setCurrentKeyframe(it.animationId, 0)
        })

        this.background.addActor(controlsGroup)

        this.background.addActor(Radar(scrollpane).also {
            it.setPosition(22f, 698f)
        })

        this.terrain.setScrollFocus(true)
    }

    override fun dispose() {
        super.dispose()

        animationManager.dispose()
        stageAssetsManager.dispose()
    }
}