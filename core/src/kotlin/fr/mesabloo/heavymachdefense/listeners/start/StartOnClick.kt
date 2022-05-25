package fr.mesabloo.heavymachdefense.listeners.start

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.screens.StartScreen

class StartOnClick(private val screen: StartScreen) : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        this.screen.tapToStart.clearActions()
        this.screen.backgroundImage.clearListeners()

        if (this.screen.tapToStart.remove()) {
            this.screen.game.justStarted = false

            menuAssetsManager.preload()
            buttonAssetsManager.preload()

            // NOTE: we know for sure that this is this screen as this system is instantiated only there
            val screen = this.screen.game.`access$currentScreen` as StartScreen
            screen.addLoadingOverlay({
                if (!assetManager.isFinished)
                    assetManager.update()
                menuAssetsManager.isFullyLoaded() && buttonAssetsManager.isFullyLoaded()
            }) {
                (this.changeScreen(lazy { SavesSelectionScreen(this, true) }) as AbstractScreen?)
                    ?.addLoadingOverlayEnd()

                this.getScreen<StartScreen>().dispose()
                this.removeScreen<StartScreen>()
            }
        }
    }
}