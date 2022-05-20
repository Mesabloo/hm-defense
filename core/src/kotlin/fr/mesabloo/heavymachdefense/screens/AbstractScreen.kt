package fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.MainGame
import ktx.app.KtxScreen

abstract class AbstractScreen(protected val game: MainGame): KtxScreen {
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