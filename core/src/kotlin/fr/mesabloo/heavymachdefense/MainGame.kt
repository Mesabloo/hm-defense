package fr.mesabloo.heavymachdefense

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.physics.box2d.Box2D
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.backgroundAssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.machAssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens.StageScreen
import fr.mesabloo.heavymachdefense.log.ColoredLogger
import ktx.app.KtxGame

class MainGame : KtxGame<AbstractScreen>() {
    override fun create() {
        // Put a custom application logger with colors
        Gdx.app.applicationLogger = ColoredLogger()
        Gdx.app.logLevel = if (DEBUG) Application.LOG_DEBUG else Application.LOG_INFO

        // Initialize Box2D right now
        Box2D.init()

        backgroundAssetsManager.init()

        super.create()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!assetManager.update())
            return

        // Set the current screen to the first stage.
        // TODO: this will be tweaked when creating a menu screen.
        if (machAssetsManager.isLoaded() && backgroundAssetsManager.isLoaded()) {
            if (this.currentScreen !is StageScreen) {
                this.addScreen(StageScreen(1))
                this.setScreen<StageScreen>()
            }
        }

        super.render()
    }

    override fun resize(width: Int, height: Int) {
        if (DEBUG)
            Gdx.app.debug(this.javaClass.simpleName, "Resizing window to ${width}x${height}")

        super.resize(width, height)
    }

    override fun dispose() {
        if (DEBUG)
            Gdx.app.debug(this.javaClass.simpleName, "Quitting application")

        super.dispose()
    }

}