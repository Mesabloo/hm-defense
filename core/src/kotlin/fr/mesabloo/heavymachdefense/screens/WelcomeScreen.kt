package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.BlinkingComponent
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.managers.assets.WelcomeAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.welcomeAssetsManager
import fr.mesabloo.heavymachdefense.systems.input.MouseInputSystem
import fr.mesabloo.heavymachdefense.systems.input.WelcomeNextScreenSystem
import fr.mesabloo.heavymachdefense.systems.input.processor.MouseInputProcessor
import fr.mesabloo.heavymachdefense.systems.rendering.BlinkingSystem
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with

class WelcomeScreen(game: MainGame, isLoading: Boolean = false) : AbstractScreen(game, isLoading) {
    lateinit var tapToStart: Entity

    private val mux: InputMultiplexer = InputMultiplexer()

    private val touchSignal: Signal<MouseInputEvent> = Signal()

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        this.mux.addProcessor(MouseInputProcessor(this.touchSignal))
        Gdx.input.inputProcessor = this.mux

        this.ui.engine.addSystem(MouseInputSystem(this.touchSignal))
        this.ui.engine.addSystem(WelcomeNextScreenSystem(this.game))
        this.ui.engine.addSystem(BlinkingSystem())
        this.ui.engine.addSystem(RenderPositionedTextures(this.ui.batch))

        this.ui.engine.entity {
            with<TextureComponent> {
                texture = welcomeAssetsManager.texture(WelcomeAssetsManager.TITLE_BACKGROUND)
                width = UI_WIDTH
                height = UI_HEIGHT
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                zIndex = 0
            }
            with<MouseInputComponent> {

            }
        }
        this.tapToStart = this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = welcomeAssetsManager.texture(WelcomeAssetsManager.TAP_TO_START)

            this.entity += textureComponent
            with<PositionComponent> {
                x = UI_WIDTH / 2f - textureComponent.width / 2f
                y = UI_HEIGHT / 2.25f - textureComponent.height / 2f
                zIndex = 15
            }
            with<BlinkingComponent> {
                frequency = 1f
            }
        }

        this.ui.show()
    }

    override fun pause() {
        super.pause()

        Gdx.input.inputProcessor = null
    }

    override fun resume() {
        super.resume()

        Gdx.input.inputProcessor = this.mux
    }

    override fun dispose() {
        super.dispose()

        welcomeAssetsManager.dispose()
    }
}