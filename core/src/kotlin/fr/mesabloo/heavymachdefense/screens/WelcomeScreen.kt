package fr.mesabloo.heavymachdefense.screens

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
import fr.mesabloo.heavymachdefense.world.UIWorld
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with

class WelcomeScreen(game: MainGame) : AbstractScreen(game) {
    private val ui = UIWorld()

    private val mux: InputMultiplexer = InputMultiplexer()

    private val touchSignal: Signal<MouseInputEvent> = Signal()

    override fun show() {
        super.show()

        this.mux.addProcessor(MouseInputProcessor(this.touchSignal))
        Gdx.input.inputProcessor = this.mux

        this.ui.engine.removeAllEntities()
        this.ui.engine.removeAllSystems()

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
                z = 0f
            }
            with<MouseInputComponent> {

            }
        }
        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = welcomeAssetsManager.texture(WelcomeAssetsManager.TAP_TO_START)

            this.entity += textureComponent
            with<PositionComponent> {
                x = UI_WIDTH / 2f - textureComponent.width / 2f
                y = UI_HEIGHT / 3f - textureComponent.height / 2f
                z = 15f
            }
            with<BlinkingComponent> {
                frequency = 1f
            }
        }

        this.ui.show()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        this.ui.resize(width, height)
    }

    override fun render(delta: Float) {
        super.render(delta)

        this.ui.render(delta)
    }
}