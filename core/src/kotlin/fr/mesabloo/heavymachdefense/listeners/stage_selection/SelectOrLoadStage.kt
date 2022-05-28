package fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Timer
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.StageScreen
import fr.mesabloo.heavymachdefense.screens.StageSelectionScreen

class SelectOrLoadStage(private val screen: StageSelectionScreen, private val index: Int) : ClickListener() {
    private fun stageScreen(game: MainGame, index: Int) =  StageScreen(
        game,
        index + 1,
        this.screen.save,
        true
    )

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        Gdx.app.debug(this.javaClass.simpleName, "Clicked on item number $index")

        if (index > this.screen.save.lastStageCompleted)
            return

        if (this.screen.scrollPane.selected == index) {
            this.screen.scrollPane.touchable = Touchable.disabled

            stageAssetsManager.preload(index + 1)
            buttonAssetsManager.preload()

            this.screen.addLoadingOverlay({
                if (!assetManager.isFinished)
                    assetManager.update()
                buttonAssetsManager.isFullyLoaded() && stageAssetsManager.isFullyLoaded()
            }) {
                this@SelectOrLoadStage.screen.background
                    .children.forEach { it.remove() }

                (this.changeScreen(stageScreen(this, index)) as AbstractScreen?)
                    ?.addLoadingOverlayEnd()

                Timer.schedule(object: Timer.Task() {
                    override fun run() {
                        this@addLoadingOverlay.removeScreen<StageSelectionScreen>()?.dispose()
                        buttonAssetsManager.dispose()
                    }
                }, 0.050f)
            }
        } else {
            this.screen.scrollPane.selected = index
        }
    }
}