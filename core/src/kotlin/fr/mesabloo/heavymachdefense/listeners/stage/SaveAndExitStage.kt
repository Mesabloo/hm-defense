package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Timer
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.startAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.StageScreen
import fr.mesabloo.heavymachdefense.screens.StartScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ktx.preferences.flush
import ktx.preferences.set

class SaveAndExitStage(private val screen: StageScreen, private val dialog: Dialog) : ClickListener() {
    private fun startScreen(game: MainGame) = StartScreen(game, true)

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        this.dialog.hide()

        val prefs = Gdx.app.getPreferences(GameSave.PREFERENCES_PATH)

        val save = this.screen.save

        prefs.flush {
            this[this@SaveAndExitStage.screen.saveIndex.toString()] = Json.encodeToString(save)
        }

        startAssetsManager.preload()
        this.screen.addLoadingOverlay({
            if (!assetManager.isFinished)
                assetManager
            startAssetsManager.isFullyLoaded()
        }) {
            (this.changeScreen(startScreen(this)) as AbstractScreen?)
                ?.addLoadingOverlayEnd()

            Timer.schedule(object : Timer.Task() {
                override fun run() {
                    this@addLoadingOverlay.removeScreen<StageScreen>()?.dispose()

                    buttonAssetsManager.dispose()
                }
            }, 0.050f)
        }
    }
}