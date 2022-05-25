package fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.listeners.start.StartOnClick
import fr.mesabloo.heavymachdefense.managers.assets.startAssetsManager
import fr.mesabloo.heavymachdefense.ui.start.Background
import fr.mesabloo.heavymachdefense.ui.start.TapToStart

class StartScreen(game: MainGame, isLoading: Boolean = false) : AbstractScreen(game, isLoading) {
    lateinit var tapToStart: Actor
        private set
    lateinit var backgroundImage: Actor
        private set

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        this.background.addActor(Background().also { this.backgroundImage = it })
        this.background.addActor(TapToStart().also { this.tapToStart = it })

        this.backgroundImage.addListener(StartOnClick(this@StartScreen))
    }

    override fun dispose() {
        super.dispose()

        startAssetsManager.dispose()
    }
}