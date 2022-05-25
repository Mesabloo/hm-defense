package fr.mesabloo.heavymachdefense.listeners.saves

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.screens.StageSelectionScreen

class SelectOrLoadSave(
    private val screen: SavesSelectionScreen,
    private val index: Int,
    private val actor: Actor,
    private val save: GameSave
) : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (this.index == this.screen.focusedIndex) {
            this.actor.removeListener(this)

            levelSelectionAssetsManager.preload()
            buttonAssetsManager.preload()

            // NOTE: we know for sure that this is this screen as this system is instantiated only there
            val screen = this.screen.game.`access$currentScreen` as SavesSelectionScreen
            screen.addLoadingOverlay({
                if (!assetManager.isFinished)
                    assetManager.update()
                levelSelectionAssetsManager.isFullyLoaded() && buttonAssetsManager.isFullyLoaded()
            }) {
                (this.changeScreen(lazy {
                    StageSelectionScreen(
                        this,
                        this@SelectOrLoadSave.save,
                        true
                    )
                }) as AbstractScreen?)
                    ?.addLoadingOverlayEnd()

                this.getScreen<SavesSelectionScreen>().dispose()
                this.removeScreen<SavesSelectionScreen>()
            }
        } else {
            this.screen.changeFocused(this.index)
        }
    }
}