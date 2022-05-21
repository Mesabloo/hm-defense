package fr.mesabloo.heavymachdefense.systems.input

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import fr.mesabloo.heavymachdefense.screens.WelcomeScreen
import ktx.ashley.allOf
import ktx.ashley.get

class WelcomeNextScreenSystem(private val game: MainGame) : IteratingSystem(allOf(MouseInputComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val mouseInputComponent = entity[MouseInputComponent.mapper]!!

        if (mouseInputComponent.leftClick) {
            Gdx.input.inputProcessor = null

            this.game.justStarted = false

            menuAssetsManager.preload()

            // NOTE: we know for sure that this is this screen as this system is instantiated only there
            val screen = this.game.`access$currentScreen` as WelcomeScreen
            screen.ui.engine.removeEntity(screen.tapToStart)
            screen.addLoadingOverlay({
                if (!assetManager.isFinished)
                    assetManager.update()
                menuAssetsManager.isFullyLoaded()
            }) {
                (this.changeScreen(lazy { MenuScreen(this, true) }) as AbstractScreen?)
                    ?.addLoadingOverlayEnd()
            }

            mouseInputComponent.leftClick = false
        }
    }
}