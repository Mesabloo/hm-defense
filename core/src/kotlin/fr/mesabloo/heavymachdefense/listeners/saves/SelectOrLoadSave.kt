package fr.mesabloo.heavymachdefense.listeners.saves

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Timer
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.screens.StageSelectionScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ktx.preferences.flush
import ktx.preferences.set
import java.util.*

class SelectOrLoadSave(
    private val screen: SavesSelectionScreen,
    private val index: Int,
    private val actor: Actor,
    private val save: GameSave
) : ClickListener() {
    private fun stageSelectionScreen(game: MainGame, saveIndex: Int) = StageSelectionScreen(
        game,
        this.save,
        saveIndex,
        true
    )

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (this.index == this.screen.focusedIndex) {
            val prefs = Gdx.app.getPreferences(GameSave.PREFERENCES_PATH)
            this.save.lastAccessedDate = Date()
            prefs.flush {
                this[this@SelectOrLoadSave.index.toString()] = Json.encodeToString(this@SelectOrLoadSave.save)
            }

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
                (this.changeScreen(stageSelectionScreen(this, this@SelectOrLoadSave.index)) as AbstractScreen?)
                    ?.addLoadingOverlayEnd()

                Timer.schedule(object: Timer.Task() {
                    override fun run() {
                        this@addLoadingOverlay.removeScreen<SavesSelectionScreen>()?.dispose()
                    }
                }, 0.050f)
            }
        } else {
            this.screen.changeFocused(this.index)
        }
    }
}