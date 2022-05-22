package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.utils.Json
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.components.saves.SaveComponent
import fr.mesabloo.heavymachdefense.components.ui.Button
import fr.mesabloo.heavymachdefense.components.ui.ButtonClickComponent
import fr.mesabloo.heavymachdefense.components.ui.OnClickListener
import fr.mesabloo.heavymachdefense.components.ui.TextComponent
import fr.mesabloo.heavymachdefense.components.ui.TextComponent.Companion.BOTTOM_RIGHT
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.MenuAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.saves.GameSave
import fr.mesabloo.heavymachdefense.saves.GameSaveJsonSerializer
import fr.mesabloo.heavymachdefense.systems.input.ButtonClickSystem
import fr.mesabloo.heavymachdefense.systems.input.MouseInputSystem
import fr.mesabloo.heavymachdefense.systems.input.listener.MenuBackClickListener
import fr.mesabloo.heavymachdefense.systems.input.listener.MenuDeleteClickListener
import fr.mesabloo.heavymachdefense.systems.input.listener.MenuNewClickListener
import fr.mesabloo.heavymachdefense.systems.input.processor.MouseInputProcessor
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.systems.rendering.ui.RenderTextSystem
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.ashley.*
import ktx.json.fromJson
import ktx.json.setSerializer
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

class MenuScreen(game: MainGame, isLoading: Boolean = false) : AbstractScreen(game, isLoading) {
    private val mux = InputMultiplexer()
    private val mouseInputSignal: Signal<MouseInputEvent> = Signal()

    private var saves: MutableList<GameSave> = mutableListOf()
    var focusedIndex: Int = 0
        private set

    private var prefs: Preferences = Gdx.app.getPreferences(GameSave.PREFERENCES_PATH)
    private val json = Json()

    init {
        this.json.setSerializer(GameSaveJsonSerializer)

        val numberOfSaves: Int = this.prefs["count"] ?: 0

        Gdx.app.debug(this.javaClass.simpleName, "Found $numberOfSaves saves in preferences")

        for (i in 0 until numberOfSaves) {
            this.saves.add(
                i,
                this.prefs.get<String>("$i")?.let { this.json.fromJson(it) } ?: GameSave()
            )
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
        this.ui.engine.addSystem(RenderTextSystem(this.ui.batch))

        this.ui.engine.entity {
            with<TextureComponent> {
                texture = menuAssetsManager.get(MenuAssetsManager.BACKGROUND)
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                zIndex = 0
            }
        }

        val lastAccessed: GameSave? = this.saves.maxByOrNull { it.lastAccessedDate }
        this.focusedIndex = lastAccessed?.let { this.saves.indexOf(it) } ?: -1

        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.BACK, false)

            with<PositionComponent> {
                x = UI_WIDTH * 1f / 4f - textureComponent.width / 2f + 32f
                y = 30f
                zIndex = 10
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
                zIndex = 10
            }
            with<ButtonClickComponent> {
                buttonKind = Button.DELETE
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                camera = this@MenuScreen.ui.camera
                listener = MenuDeleteClickListener(this@MenuScreen)
            }
            this.entity += textureComponent
        }
        this.ui.engine.entity {
            val textureComponent = TextureComponent()
            textureComponent.texture = buttonAssetsManager.texture(Button.NEW, false)

            with<PositionComponent> {
                x = UI_WIDTH * 3f / 4f - textureComponent.width / 2f - 32f
                y = 30f
                zIndex = 10
            }
            with<ButtonClickComponent> {
                buttonKind = Button.NEW
            }
            with<MouseInputComponent> {}
            with<OnClickListener> {
                camera = this@MenuScreen.ui.camera
                listener = MenuNewClickListener(this@MenuScreen)
            }
            this.entity += textureComponent
        }

        var currentY = BASE_SAVE_Y
        for (save in this.saves) {
            currentY -= this.createSave(save, currentY) { it == lastAccessed } + SAVE_PADDING
        }
    }

    override fun dispose() {
        super.dispose()

        menuAssetsManager.dispose()
        buttonAssetsManager.dispose()
    }

    fun addSave(save: GameSave) {
        val lastSave = this.saves.lastOrNull()

        val currentY = this.ui.engine.getEntitiesFor(
            allOf(
                SaveComponent::class,
                PositionComponent::class,
                TextureComponent::class
            ).get()
        )
            .firstOrNull {
                it[SaveComponent.mapper]!!.save.creationDate == lastSave?.creationDate
            }
            ?.let {
                it[PositionComponent.mapper]!!.y - SAVE_PADDING
            }
            ?: BASE_SAVE_Y

        this.saves.add(save)
        val lastIndex = this.saves.lastIndex
        this.focusedIndex = lastIndex

        this.prefs.flush {
            this["$lastIndex"] = this@MenuScreen.json.toJson(save)
            this["count"] = lastIndex + 1
        }

        this.createSave(save, currentY) { true }
    }

    private fun createSave(save: GameSave, currentY: Float, isFocused: (GameSave) -> Boolean): Float {
        var height by Delegates.notNull<Float>()

        val currentlyFocused = isFocused(save)

        val textureComponent = TextureComponent()
        textureComponent.texture = menuAssetsManager.getSlot(currentlyFocused)

        val positionX = UI_WIDTH / 2f - textureComponent.width / 2f
        val positionY = currentY - textureComponent.height

        val dateFormatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        val newEntity = this.ui.engine.entity {
            with<SaveComponent> {
                this.save = save
                this.focused = currentlyFocused
            }
            with<PositionComponent> {
                x = positionX
                y = positionY
                zIndex = 20
            }
            this.entity += textureComponent

            height = textureComponent.height
        }
        this.ui.engine.entity {
            // creation date
            with<SaveComponent> {
                this.save = save
            }
            with<TextComponent> {
                message = dateFormatter.format(save.creationDate)
                font = fontManager.bitmapFonts[FontManager.SET01B]!!
            }
            with<PositionComponent> {
                x = positionX + 150f
                y = positionY + 65f
                zIndex = 30
            }
        }
        this.ui.engine.entity {
            // last access date
            with<SaveComponent> {
                this.save = save
            }
            with<TextComponent> {
                message = dateFormatter.format(save.lastAccessedDate)
                font = fontManager.bitmapFonts[FontManager.SET01B]!!
            }
            with<PositionComponent> {
                x = positionX + 150f
                y = positionY + 44f
                zIndex = 30
            }
        }
        this.ui.engine.entity {
            // stage number
            with<SaveComponent> {
                this.save = save
            }
            with<TextComponent> {
                message = (save.lastStageCompleted + 1).toString().padStart(2, '0')
                font = fontManager.bitmapFonts[FontManager.CREDITS]!!
            }
            with<PositionComponent> {
                x = positionX + 377f
                y = positionY + 63f
                zIndex = 30
            }
        }
        this.ui.engine.entity {
            // credits
            with<SaveComponent> {
                this.save = save
            }
            with<TextComponent> {
                message = save.credits.toString()
                font = fontManager.bitmapFonts[FontManager.CREDITS]!!
                align = BOTTOM_RIGHT
            }
            with<PositionComponent> {
                x = positionX + 430f
                y = positionY + 37f
                zIndex = 30
            }
        }
        this.ui.engine.entity {
            // name
            with<SaveComponent> {
                this.save = save
            }
            with<TextComponent> {
                message = save.name
                font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_24]!!
            }
            with<PositionComponent> {
                x = positionX + 86f
                y = positionY + 102f
                zIndex = 30
            }
        }

        // TODO: render text behind loading screen (take Z-index in account)

        if (currentlyFocused) {
            this.ui.engine.getEntitiesFor(allOf(SaveComponent::class, TextureComponent::class).get()).forEach {
                if (it != newEntity) {
                    it[SaveComponent.mapper]!!.focused = false
                    it[TextureComponent.mapper]!!.texture = menuAssetsManager.getSlot(false)
                }
            }
        }

        return height
    }

    @Suppress("GDXKotlinMissingFlush")
    fun removeSaveFromIndex(saveIndex: Int) {
        if (saveIndex < 0 || saveIndex >= this.saves.size)
            return

        this.ui.engine.removeAllEntities(allOf(SaveComponent::class).get())
        this.saves.removeAt(saveIndex)

        this.prefs.flush {
            this["count"] = this@MenuScreen.saves.size

            this.remove("$saveIndex")

            for (index in saveIndex until this@MenuScreen.saves.size) {
                this["$index"] = this.getString("${index + 1}")
            }
            this.remove("${this@MenuScreen.saves.size}")
        }

        val newlyFocused = this.saves.maxByOrNull { it.lastAccessedDate }
        this.focusedIndex = newlyFocused?.let { this.saves.indexOf(it) } ?: -1

        var currentY = BASE_SAVE_Y
        for (save in this.saves) {
            currentY -= this.createSave(save, currentY) { it == newlyFocused } + SAVE_PADDING
        }
    }

    private companion object {
        const val SAVE_PADDING = 10f
        const val BASE_SAVE_Y = UI_HEIGHT * 8f / 10f - 12f
    }
}