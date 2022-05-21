package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.animation.AmortizedTransformToComponent
import fr.mesabloo.heavymachdefense.components.animation.ScaleComponent
import fr.mesabloo.heavymachdefense.components.LoadingPartComponent
import fr.mesabloo.heavymachdefense.managers.assets.LoadingAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.loadingAssetsManager
import fr.mesabloo.heavymachdefense.systems.LoadingFinishedSystem
import fr.mesabloo.heavymachdefense.systems.SignalWhenFinishedLoadingSystem
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.systems.rendering.animation.ScaleAnimationSystem
import fr.mesabloo.heavymachdefense.systems.rendering.animation.TransformAnimationSystem
import fr.mesabloo.heavymachdefense.world.UIWorld
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.app.KtxScreen
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with

abstract class AbstractScreen(protected val game: MainGame, isLoading: Boolean = false) : KtxScreen {
    val ui = UIWorld()

    private val loadingDoneSignal: Signal<Unit> = Signal()
    private val loadingAnimationFinishedSignal: Signal<Unit> = Signal()
    private val loadingAnimationFinishedSignal2: Signal<Unit> = Signal()

    var isLoading: Boolean = isLoading
        private set

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

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        this.ui.resize(width, height)
    }

    override fun render(delta: Float) {
        super.render(delta)

        this.ui.render(delta)
    }

    fun addLoadingOverlay(finishedLoading: () -> Boolean, switchScreen: MainGame.() -> Unit) {
        this.isLoading = true

        this.ui.engine.addSystem(TransformAnimationSystem(this.loadingAnimationFinishedSignal))
        this.ui.engine.addSystem(LoadingFinishedSystem(this.loadingAnimationFinishedSignal) {
            this.game.switchScreen()
        })
        this.ui.engine.addSystem(SignalWhenFinishedLoadingSystem(this.loadingDoneSignal, finishedLoading))
        this.ui.engine.addSystem(RenderPositionedTextures(this.ui.batch))

        this.ui.engine.entity {
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
                z = 5000f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5000f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5001f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5001f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5002f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
    }

    private lateinit var loadingOverlaySystems: List<EntitySystem>

    fun addLoadingOverlayEnd() {
        this.isLoading = true
        //this.loadingDoneSignal.add { _, _ -> this.game.switchScreen() }

        this.loadingOverlaySystems = listOf(
            TransformAnimationSystem(this.loadingAnimationFinishedSignal2),
            ScaleAnimationSystem(this.loadingAnimationFinishedSignal),
            RenderPositionedTextures(this.ui.batch)
        )
        for (system in this.loadingOverlaySystems) {
            this.ui.engine.addSystem(system)
        }

        this.loadingAnimationFinishedSignal.add { _, _ ->
            Gdx.app.debug(this.javaClass.simpleName, "Finished scaling animation")

            this.isLoading = false
            this.show()
        }
        this.loadingAnimationFinishedSignal2.add { _, _ ->
            Gdx.app.debug(this.javaClass.simpleName, "Removing entities")

            this@AbstractScreen.removeLoadingOverlayEnd()
        }

        this.ui.engine.entity {
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
                z = 5000f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5000f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5001f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5001f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
        this.ui.engine.entity {
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
                z = 5002f
            }
            with<LoadingPartComponent> {}
            this.entity += textureComponent
        }
    }

    private fun removeLoadingOverlayEnd() {
        this.ui.engine.removeAllEntities(allOf(LoadingPartComponent::class).get())

        for (system in this.loadingOverlaySystems) {
            this.ui.engine.removeSystem(system)
        }
    }

    companion object {
        const val LOADING_DURATION = 1.1f
        const val LOADING_END_DELAY = 0.5f
    }
}