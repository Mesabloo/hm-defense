package fr.mesabloo.heavymachdefense

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.log.ColoredLogger
import ktx.app.KtxApplicationAdapter

class MainGame : KtxApplicationAdapter {
    override fun create() {
        Gdx.app.applicationLogger = ColoredLogger()
        Gdx.app.logLevel = if (DEBUG) Application.LOG_DEBUG else Application.LOG_INFO

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun render() {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }

}