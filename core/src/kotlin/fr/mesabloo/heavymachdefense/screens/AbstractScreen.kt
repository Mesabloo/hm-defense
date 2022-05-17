package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.Gdx
import ktx.app.KtxScreen

abstract class AbstractScreen: KtxScreen {
    override fun hide() {
        Gdx.app.debug(this.javaClass.simpleName, "Hiding application")
    }

    override fun show() {
        Gdx.app.debug(this.javaClass.simpleName, "Showing application")
    }

    override fun pause() {
        Gdx.app.debug(this.javaClass.simpleName, "Pausing application")
    }

    override fun resume() {
        Gdx.app.debug(this.javaClass.simpleName, "Resuming application")
    }
}