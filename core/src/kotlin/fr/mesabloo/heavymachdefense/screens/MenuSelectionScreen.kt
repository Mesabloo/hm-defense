package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.components.ui.*
import fr.mesabloo.heavymachdefense.data.getBackgroundForLevel
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.LevelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.systems.input.ButtonClickSystem
import fr.mesabloo.heavymachdefense.systems.input.MouseInputSystem
import fr.mesabloo.heavymachdefense.systems.input.listener.stage_select.SelectBackClickListener
import fr.mesabloo.heavymachdefense.systems.input.processor.MouseInputProcessor
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.systems.rendering.ui.RenderTextSystem
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with

class MenuSelectionScreen(game: MainGame, private val save: GameSave, isLoading: Boolean = false) :
    AbstractScreen(game, isLoading) {
    private val mux = InputMultiplexer()
    private val mouseInputSignal: Signal<MouseInputEvent> = Signal()

    override fun setupInputProcessor() {
        Gdx.input.inputProcessor = this.mux
    }

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        this.mux.addProcessor(MouseInputProcessor(this.mouseInputSignal))

        this.ui.engine.addSystem(MouseInputSystem(this.mouseInputSignal))
        this.ui.engine.addSystem(ButtonClickSystem())
        this.ui.engine.addSystem(RenderTextSystem(this.ui.batch))
        this.ui.engine.addSystem(RenderPositionedTextures(this.ui.batch))

        this.ui.engine.entity {
            with<TextureComponent> {
                texture = levelSelectionAssetsManager.texture(LevelSelectionAssetsManager.BACKGROUND)
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                zIndex = 0
            }
        }
        this.ui.engine.entity {
            with<TextureComponent> {
                texture = levelSelectionAssetsManager.texture(LevelSelectionAssetsManager.FOREGROUND)
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                zIndex = 25
            }
        }

        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.BACK, false)

            with<PositionComponent> {
                x = UI_WIDTH * 1f / 4f - textureComponent.width / 2f + 32f
                y = 30f
                zIndex = 100
            }
            with<ButtonClickComponent> {
                buttonKind = Button.BACK
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                viewport = this@MenuSelectionScreen.ui.viewport
                listener = SelectBackClickListener(this@MenuSelectionScreen, this@entity.entity)
            }
            this.entity += textureComponent
        }
        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.SUPPORT, false)

            with<PositionComponent> {
                x = UI_WIDTH * 2f / 4f - textureComponent.width / 2f
                y = 30f
                zIndex = 100
            }
            with<ButtonClickComponent> {
                buttonKind = Button.SUPPORT
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                viewport = this@MenuSelectionScreen.ui.viewport
                listener = {}
            }
            this.entity += textureComponent
        }
        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.OK, false)

            with<PositionComponent> {
                x = UI_WIDTH * 3f / 4f - textureComponent.width / 2f - 32f
                y = 30f
                zIndex = 100
            }
            with<ButtonClickComponent> {
                buttonKind = Button.OK
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                viewport = this@MenuSelectionScreen.ui.viewport
                listener = {}
            }
            this.entity += textureComponent
        }

        ////////////////////////////////////////////////////

        val height =
            levelSelectionAssetsManager.button(LevelSelectionAssetsManager.Companion.ButtonState.NORMAL).regionHeight

        val lastCompleted = this.save.lastStageCompleted

        val offset =
            if (lastCompleted <= 4) 0f
            else if (lastCompleted >= 75) 70f * height
            else -4f * height + lastCompleted.toFloat() * height
        var currentY = STAGE_UI_HEIGHT + offset - height * 5f / 4f

        (1..80).forEach {
            this.addStageButton(it, currentY, it > this.save.lastStageCompleted + 1)
            currentY -= height + STAGE_PADDING
        }
    }

    private fun addStageButton(level: Int, currentY: Float, isDisabled: Boolean) {
        val backgrounds = levelSelectionAssetsManager.stageBackground(getBackgroundForLevel(level))

        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = levelSelectionAssetsManager.button(
                if (isDisabled) LevelSelectionAssetsManager.Companion.ButtonState.DISABLED
                else LevelSelectionAssetsManager.Companion.ButtonState.NORMAL
            )

            with<PositionComponent> {
                x = 128f
                y = currentY
                zIndex = 5
            }
            with<ButtonClickComponent> {}
            with<MouseInputComponent> {}
            with<OnClickListener> {
                viewport = this@MenuSelectionScreen.ui.viewport
                listener = {}
            }
            with<StageButtonComponent> {
                this.level = level
            }
            this.entity += textureComponent
        }
        this.ui.engine.entity {
            with<TextComponent> {
                message = "Stage $level"
                font = fontManager.bitmapFonts[
                        if (isDisabled) FontManager.TREBUCHET_MS_BOLD_28_BLACK
                        else FontManager.TREBUCHET_MS_BOLD_28_BLUE
                ]!!
                align = TextComponent.CENTER_Y.or(TextComponent.LEFT_X)
            }
            with<PositionComponent> {
                x = 225f
                y = currentY + 53f
                zIndex = 10
            }
            with<StageButtonComponent> {
                this.level = level
            }
        }

        var firstWidth: Float
        this.ui.engine.entity {
            with<TextureComponent> {
                texture = backgrounds.first

                firstWidth = this.width
            }
            with<PositionComponent> {
                x = 414f
                y = currentY + 8f
                zIndex = 10
            }
            with<StageButtonComponent> {
                this.level = level
            }
        }
        this.ui.engine.entity {
            with<TextureComponent> {
                texture = backgrounds.second
            }
            with<PositionComponent> {
                x = 414f + firstWidth
                y = currentY + 8f
                zIndex = 10
            }
            with<StageButtonComponent> {
                this.level = level
            }
        }
    }

    override fun dispose() {
        super.dispose()

        levelSelectionAssetsManager.dispose()
    }

    private companion object {
        const val STAGE_UI_HEIGHT = UI_HEIGHT * 8f / 10f

        //const val STAGE_UI_WIDTH = UI_WIDTH * 6f / 10f
        const val STAGE_PADDING = 0f
    }
}