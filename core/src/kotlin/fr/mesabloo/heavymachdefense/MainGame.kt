package fr.mesabloo.heavymachdefense
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.physics.box2d.Box2D
import fr.mesabloo.heavymachdefense.log.ColoredLogger
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.loadingAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.welcomeAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.WelcomeScreen
import ktx.app.KtxGame

class MainGame : KtxGame<AbstractScreen>() {
    @PublishedApi
    internal var `access$currentScreen`: Screen
        get() = currentScreen
        set(value) {
            currentScreen = value
        }

    var justStarted: Boolean = true

    override fun create() {
        // Put a custom application logger with colors
        Gdx.app.applicationLogger = ColoredLogger()
        Gdx.app.logLevel = if (DEBUG) Application.LOG_DEBUG else Application.LOG_INFO

        // Initialize Box2D right now
        Box2D.init()

        // Load the minimal set of assets needed:
        // - all the assets for the loading screen
        // - assets for the welcome screen

        welcomeAssetsManager.preload()
        loadingAssetsManager.preload()
        fontManager.init()

        super.create()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (!assetManager.isFinished)
            assetManager.update()

        if (welcomeAssetsManager.isFullyLoaded() && loadingAssetsManager.isFullyLoaded() && justStarted) {
            changeScreen(lazy { WelcomeScreen(this) })
        }

        super.render()
    }

    inline fun <reified T : AbstractScreen> changeScreen(newScreen: Lazy<T>): T? {
        if (this.`access$currentScreen` !is T) {
            if (!this.containsScreen<T>()) {
                this.addScreen(newScreen.value)
            }
            this.setScreen<T>()
            return this.getScreen()
        }
        return null
    }

    override fun resize(width: Int, height: Int) {
        if (DEBUG)
            Gdx.app.debug(this.javaClass.simpleName, "Resizing window to ${width}x${height}")

        super.resize(width, height)
    }

    override fun dispose() {
        super.dispose()

        if (DEBUG)
            Gdx.app.debug(this.javaClass.simpleName, "Quitting application")

        assetManager.dispose()

        fontManager.dispose()
    }
}