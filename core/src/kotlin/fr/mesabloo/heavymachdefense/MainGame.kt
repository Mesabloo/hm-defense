package fr.mesabloo.heavymachdefense

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2D
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.AssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens.StageScreen
import fr.mesabloo.heavymachdefense.log.ColoredLogger
import ktx.app.KtxGame

class MainGame : KtxGame<Screen>() {
    private lateinit var batch : SpriteBatch

    override fun create() {
        // Put a custom application logger with colors
        Gdx.app.applicationLogger = ColoredLogger()
        Gdx.app.logLevel = if (DEBUG) Application.LOG_DEBUG else Application.LOG_INFO

        // Initialize Box2D right now
        Box2D.init()
        AssetsManager.init()

        this.batch = SpriteBatch()

        // Set the current screen to the first stage.
        // TODO: this will be tweaked when creating a menu screen.
        this.currentScreen = StageScreen(this.batch, 1)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        super.render()
    }

    override fun resize(width: Int, height: Int) {
        if (DEBUG)
            Gdx.app.debug("MainGame", "Resizing window to ${width}x${height}")
    }

    override fun dispose() {
        if (DEBUG)
            Gdx.app.debug("MainGame", "Quitting application")

        super.dispose()
    }

}