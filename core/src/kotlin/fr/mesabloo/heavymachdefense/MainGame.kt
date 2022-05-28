package fr.mesabloo.heavymachdefense

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.physics.box2d.Box2D
import fr.mesabloo.heavymachdefense.log.ColoredLogger
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.debugAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.loadingAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.startAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.StartScreen
import ktx.app.KtxGame

class MainGame : KtxGame<AbstractScreen>() {
    @PublishedApi
    internal var `access$currentScreen`: Screen
        get() = currentScreen
        set(value) {
            currentScreen = value
        }

    var justStarted: Boolean = true

    private val startingScreen by lazy { StartScreen(this) }

    override fun create() {
        // Put a custom application logger with colors
        Gdx.app.applicationLogger = ColoredLogger()
        Gdx.app.logLevel = if (DEBUG) Application.LOG_DEBUG else Application.LOG_INFO

        // Initialize Box2D right now
        Box2D.init()

        // Load the minimal set of assets needed:
        // - all the assets for the loading screen
        // - assets for the welcome screen

        startAssetsManager.preload()
        loadingAssetsManager.preload()
        fontManager.init()

        ifDebug {
            debugAssetsManager.preload()
        }

        super.create()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        assetManager.update()

        if (this.justStarted && debugAssetsManager.isFullyLoaded() && startAssetsManager.isFullyLoaded() && loadingAssetsManager.isFullyLoaded()) {
            this.justStarted = false
            (changeScreen(this.startingScreen) as AbstractScreen?)?.removeLoadingOverlayEnd()
        }

        super.render()
    }

    inline fun <reified T : AbstractScreen> changeScreen(newScreen: T): T? =
        if (this.`access$currentScreen` !is T) {
            if (!this.containsScreen<T>()) {
                this.addScreen(newScreen)
            }
            this.setScreen<T>()

            this.getScreen()
        } else null

    override fun resize(width: Int, height: Int) {
        Gdx.app.debug(this.javaClass.simpleName, "Resizing window to ${width}x${height}")

        super.resize(width, height)
    }

    override fun dispose() {
        super.dispose()

        Gdx.app.debug(this.javaClass.simpleName, "Quitting application")

        assetManager.dispose()

        fontManager.dispose()
    }
}