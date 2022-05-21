package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.utils.Json
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.components.ui.Button
import fr.mesabloo.heavymachdefense.components.ui.ButtonClickComponent
import fr.mesabloo.heavymachdefense.components.ui.OnClickListener
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.managers.assets.MenuAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.saves.GameSave
import fr.mesabloo.heavymachdefense.systems.input.ButtonClickSystem
import fr.mesabloo.heavymachdefense.systems.input.MouseInputSystem
import fr.mesabloo.heavymachdefense.systems.input.listener.MenuBackClickListener
import fr.mesabloo.heavymachdefense.systems.input.processor.MouseInputProcessor
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with
import ktx.preferences.get

class MenuScreen(game: MainGame, isLoading: Boolean = false) : AbstractScreen(game, isLoading) {
    private val mux = InputMultiplexer()
    private val mouseInputSignal: Signal<MouseInputEvent> = Signal()

    private var saves: MutableList<GameSave> = mutableListOf()

    init {
        val json = Json()
        val prefs = Gdx.app.getPreferences(GameSave.PREFERENCES_PATH)
        val numberOfSaves: Int = prefs["count"] ?: 0

        for (i in 0 until numberOfSaves) {
            this.saves[i] = prefs.get<String>("$i")?.let { json.fromJson(GameSave::class.java, it) } ?: GameSave()
        }
        this.saves.sortBy { it.creationDate }
    }

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        mux.addProcessor(MouseInputProcessor(this.mouseInputSignal))
        Gdx.input.inputProcessor = mux

        this.ui.engine.addSystem(MouseInputSystem(this.mouseInputSignal))
        this.ui.engine.addSystem(ButtonClickSystem())
        // NOTE: this should have already been added by the loading component
        this.ui.engine.addSystem(RenderPositionedTextures(this.ui.batch))

        this.ui.engine.entity {
            with<TextureComponent> {
                texture = menuAssetsManager.get(MenuAssetsManager.BACKGROUND)
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                z = 0f
            }
        }

        val lastAccessed: GameSave? = this.saves.maxByOrNull { it.lastAccessedDate }

        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.BACK, false)

            with<PositionComponent> {
                x = UI_WIDTH * 1f / 4f - textureComponent.width / 2f + 32f
                y = 30f
                z = 10f
            }
            with<ButtonClickComponent> {
                buttonKind = Button.BACK
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                camera = this@MenuScreen.ui.camera
                listener = MenuBackClickListener(this@MenuScreen, this@entity.entity)
            }
            this.entity += textureComponent
        }
        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.DELETE, false)

            with<PositionComponent> {
                x = UI_WIDTH * 2f / 4f - textureComponent.width / 2f
                y = 30f
                z = 10f
            }
            with<ButtonClickComponent> {
                buttonKind = Button.DELETE
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                camera = this@MenuScreen.ui.camera
                listener = {

                }
            }
            this.entity += textureComponent
        }
        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.NEW, false)

            with<PositionComponent> {
                x = UI_WIDTH * 3f / 4f - textureComponent.width / 2f - 32f
                y = 30f
                z = 10f
            }
            with<ButtonClickComponent> {
                buttonKind = Button.NEW
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                camera = this@MenuScreen.ui.camera
                listener = {

                }
            }
            this.entity += textureComponent
        }

        run {
            var currentX = 0f
            var currentY = 0f


            for (save in this.saves) {
                // TODO: add entities to position every info
                //       + select last accessed by default
            }
        }
    }

    override fun dispose() {
        super.dispose()

        menuAssetsManager.dispose()
        buttonAssetsManager.dispose()
    }
}