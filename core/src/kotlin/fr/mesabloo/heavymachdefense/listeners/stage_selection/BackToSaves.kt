package fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.screens.StageSelectionScreen

class BackToSaves(private val screen: StageSelectionScreen, private val actor: Actor) : ClickListener() {
    private fun savesSelectionScreen(game: MainGame) = SavesSelectionScreen(game, true)

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        this.actor.removeListener(this)

        menuAssetsManager.preload()
        buttonAssetsManager.preload()

        this.screen.addLoadingOverlay({
            if (!assetManager.isFinished)
                assetManager.update()
            menuAssetsManager.isFullyLoaded() && buttonAssetsManager.isFullyLoaded()
        }) {
            this@BackToSaves.screen.background
                .children.forEach { it.remove() }

            (this.changeScreen(savesSelectionScreen(this)) as AbstractScreen?)
                ?.addLoadingOverlayEnd()

            this.getScreen<StageSelectionScreen>().dispose()
            this.removeScreen<StageSelectionScreen>()
        }
    }
}