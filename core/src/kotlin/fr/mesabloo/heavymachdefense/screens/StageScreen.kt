package fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.scenes.scene2d.Group
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.ui.stage.*
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH

class StageScreen(game: MainGame, private val level: Int, private val save: GameSave, isLoading: Boolean = false) :
    AbstractScreen(game, isLoading) {
    private lateinit var title: Title

    override fun show() {
        super.show()

        if (this.isLoading)
            return

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

            it.setPosition(UI_WIDTH / 2f - it.width / 2f, 130f)
        })

        this.background.addActor(controlsGroup)
    }
}