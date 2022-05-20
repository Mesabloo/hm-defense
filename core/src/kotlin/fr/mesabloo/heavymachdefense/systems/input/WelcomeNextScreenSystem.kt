package fr.mesabloo.heavymachdefense.systems.input

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.welcomeAssetsManager
import fr.mesabloo.heavymachdefense.screens.LoadingScreen
import ktx.ashley.allOf
import ktx.ashley.get

class WelcomeNextScreenSystem(private val game: MainGame): IteratingSystem(allOf(MouseInputComponent::class).get()) {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            val mouseInputComponent = entity[MouseInputComponent.mapper]!!

            if (mouseInputComponent.leftClick) {
                this.game.changeScreen(lazy {
                    welcomeAssetsManager.dispose()

                    LoadingScreen(this.game) {
                        menuAssetsManager.isFullyLoaded()
                    }
                })
            }
        }
    }
}