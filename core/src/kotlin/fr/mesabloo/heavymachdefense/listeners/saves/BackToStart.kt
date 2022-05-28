package fr.mesabloo.heavymachdefense.listeners.saves

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.startAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.screens.StartScreen

class BackToStart(private val screen: SavesSelectionScreen, private val actor: Actor) : ClickListener() {
    private fun startingScreen(game: MainGame) = StartScreen(game, true)

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        this.actor.removeListener(this)

        startAssetsManager.preload()

        this.screen.addLoadingOverlay({
            if (!assetManager.isFinished)
                assetManager.update()
            startAssetsManager.isFullyLoaded()
        }) {
            this@BackToStart.screen.background
                .children.forEach { it.remove() }

            (this.changeScreen(startingScreen(this)) as AbstractScreen?)
                ?.addLoadingOverlayEnd()

            this.getScreen<SavesSelectionScreen>().dispose()
            this.removeScreen<SavesSelectionScreen>()

            buttonAssetsManager.dispose()
        }
    }
}