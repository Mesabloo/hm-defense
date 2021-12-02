package fr.mesabloo.heavymachdefense

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.log.ColoredLogger
import ktx.app.KtxApplicationAdapter
import ktx.log.debug

class MainGame : KtxApplicationAdapter {
    override fun create() {
        Gdx.app.applicationLogger = ColoredLogger()
        Gdx.app.logLevel = if (DEBUG) Application.LOG_DEBUG else Application.LOG_INFO



        if (DEBUG) {
            debug(javaClass.canonicalName) { "Hello" }
        }

        TODO("Not yet implemented")
    }

    override fun resize(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun render() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

}