package fr.mesabloo.heavymachdefense.entities.ui

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import fr.mesabloo.heavymachdefense.components.LoadingPartComponent
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.animation.AmortizedTransformToComponent
import fr.mesabloo.heavymachdefense.components.animation.ScaleComponent
import fr.mesabloo.heavymachdefense.managers.assets.LoadingAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.loadingAssetsManager
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with

private const val LOADING_DURATION = 0.65f
private const val LOADING_END_DELAY = 0.5f

fun createLoadingClose(engine: Engine): List<Entity> = listOf(
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_LEFT)

        with<AmortizedTransformToComponent> {
            destinationX = 0f
            destinationY = 0f
            duration = LOADING_DURATION
        }
        with<PositionComponent> {
            x = -textureComponent.width
            y = 0f
            zIndex = 5000
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_RIGHT)

        with<AmortizedTransformToComponent> {
            destinationX = UI_WIDTH - textureComponent.width
            destinationY = 0f
            duration = LOADING_DURATION
        }
        with<PositionComponent> {
            x = UI_WIDTH
            y = 0f
            zIndex = 5000
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_TOP)

        with<AmortizedTransformToComponent> {
            destinationX = 0f
            destinationY = UI_HEIGHT - textureComponent.height
            duration = LOADING_DURATION
        }
        with<PositionComponent> {
            x = 0f
            y = UI_HEIGHT + textureComponent.height
            zIndex = 5001
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_BOTTOM)

        with<AmortizedTransformToComponent> {
            destinationX = 0f
            destinationY = 0f
            duration = LOADING_DURATION
        }
        with<PositionComponent> {
            x = 0f
            y = -textureComponent.height - textureComponent.height
            zIndex = 5001
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_CENTER)

        with<AmortizedTransformToComponent> {
            destinationX = UI_WIDTH / 2f - textureComponent.width / 2f
            destinationY = UI_HEIGHT / 2f - textureComponent.height / 2.5f + 2f
            duration = LOADING_DURATION
        }
        with<PositionComponent> {
            x = UI_WIDTH / 2f - textureComponent.width / 2f
            y = -1158f
            zIndex = 5002
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    }
)

fun createLoadingOpen(engine: Engine): List<Entity> = listOf(
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_LEFT)

        with<AmortizedTransformToComponent> {
            destinationX = -textureComponent.width
            destinationY = 0f
            duration = LOADING_DURATION
            delay = LOADING_END_DELAY
        }
        with<PositionComponent> {
            x = 0f
            y = 0f
            zIndex = 5000
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_RIGHT)

        with<AmortizedTransformToComponent> {
            destinationX = UI_WIDTH
            destinationY = 0f
            duration = LOADING_DURATION
            delay = LOADING_END_DELAY
        }
        with<PositionComponent> {
            x = UI_WIDTH - textureComponent.width
            y = 0f
            zIndex = 5000
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_TOP)

        with<AmortizedTransformToComponent> {
            destinationX = 0f
            destinationY = UI_HEIGHT + textureComponent.height
            duration = LOADING_DURATION
            delay = LOADING_END_DELAY
        }
        with<PositionComponent> {
            x = 0f
            y = UI_HEIGHT - textureComponent.height
            zIndex = 5001
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_BOTTOM)

        with<AmortizedTransformToComponent> {
            destinationX = 0f
            destinationY = -textureComponent.height - textureComponent.height
            duration = LOADING_DURATION
            delay = LOADING_END_DELAY
        }
        with<PositionComponent> {
            x = 0f
            y = 0f
            zIndex = 5001
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    },
    engine.entity {
        val textureComponent = TextureComponent()
        textureComponent.texture = loadingAssetsManager.texture(LoadingAssetsManager.LOADING_CENTER)

        with<AmortizedTransformToComponent> {
            destinationX = UI_WIDTH / 2f - textureComponent.width / 2f
            destinationY = -1158f
            duration = LOADING_DURATION
            delay = LOADING_END_DELAY
        }
        with<ScaleComponent> {
            destinationX = 0.75f
            destinationY = 0.75f
            duration = 0.25f
            delay = 0f
        }
        with<PositionComponent> {
            x = UI_WIDTH / 2f - textureComponent.width / 2f
            y = UI_HEIGHT / 2f - textureComponent.height / 2.5f + 2f
            zIndex = 5002
        }
        with<LoadingPartComponent> {}
        this.entity += textureComponent
    }
)