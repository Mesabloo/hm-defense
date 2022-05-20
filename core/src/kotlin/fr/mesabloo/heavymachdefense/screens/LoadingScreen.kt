package fr.mesabloo.heavymachdefense.screens

import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.world.UIWorld

class LoadingScreen(game: MainGame, val finishLoading: () -> Boolean = { false }) : AbstractScreen(game) {
    private val ui = UIWorld()

    init {

    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        this.ui.resize(width, height)
    }

    override fun render(delta: Float) {
        super.render(delta)

        this.ui.render(delta)
    }

    override fun dispose() {
        this.ui.dispose()

        super.dispose()
    }
}