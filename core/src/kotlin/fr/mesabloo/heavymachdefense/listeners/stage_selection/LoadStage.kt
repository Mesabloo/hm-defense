package fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.StageScreen
import fr.mesabloo.heavymachdefense.screens.StageSelectionScreen

class LoadStage(private val screen: StageSelectionScreen) : ClickListener() {
    private fun stageScreen(game: MainGame, index: Int) = StageScreen(
        game,
        index + 1,
        this.screen.save,
        true
    )

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        val index = this.screen.scrollPane.selected

        this.screen.scrollPane.touchable = Touchable.disabled

        stageAssetsManager.preload(index + 1)
        buttonAssetsManager.preload()

        this.screen.addLoadingOverlay({
            if (!assetManager.isFinished)
                assetManager.update()
            buttonAssetsManager.isFullyLoaded() && stageAssetsManager.isFullyLoaded()
        }) {
            this@LoadStage.screen.background
                .children.forEach { it.remove() }

            (this.changeScreen(stageScreen(this, index)) as AbstractScreen?)
                ?.addLoadingOverlayEnd()

            this.getScreen<StageSelectionScreen>().dispose()
            this.removeScreen<StageSelectionScreen>()
        }
    }
}