package fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonWriter
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.data.GameSaveJsonSerializer
import fr.mesabloo.heavymachdefense.listeners.saves.BackToStart
import fr.mesabloo.heavymachdefense.listeners.saves.DeleteSave
import fr.mesabloo.heavymachdefense.listeners.saves.NewSave
import fr.mesabloo.heavymachdefense.listeners.saves.SelectOrLoadSave
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.ui.common.BackButton
import fr.mesabloo.heavymachdefense.ui.common.DeleteButton
import fr.mesabloo.heavymachdefense.ui.common.NewButton
import fr.mesabloo.heavymachdefense.ui.saves.*
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.json.fromJson
import ktx.json.setSerializer
import ktx.preferences.flush
import ktx.preferences.get
import ktx.preferences.set
import kotlin.properties.Delegates

class SavesSelectionScreen(game: MainGame, isLoading: Boolean = false) : AbstractScreen(game, isLoading) {
    private var saves: MutableList<GameSave> = mutableListOf()
    var focusedIndex: Int = 0
    val numberOfSaves: Int
        get() = saves.size

    private var prefs: Preferences = Gdx.app.getPreferences(GameSave.PREFERENCES_PATH)
    private val json = Json()

    init {
        this.json.setOutputType(JsonWriter.OutputType.json)
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

        this.background.addActor(Background())
        this.background.addActor(BackButton().also {
            it.setPosition(UI_WIDTH * 1f / 4f - it.width / 2f + 32f, 30f)
            it.addListener(BackToStart(this, it))
        })
        this.background.addActor(DeleteButton().also {
            it.setPosition(UI_WIDTH * 2f / 4f - it.width / 2f, 30f)
            it.addListener(DeleteSave(this))
        })
        this.background.addActor(NewButton().also {
            it.setPosition(UI_WIDTH * 3f / 4f - it.width / 2f - 32f, 30f)
            it.addListener(NewSave(this))
        })

        val lastAccessed: GameSave? = this.saves.maxByOrNull { it.lastAccessedDate }
        this.focusedIndex = lastAccessed?.let { this.saves.indexOf(it) } ?: -1

        var currentY = BASE_SAVE_Y
        this.saves.forEachIndexed { index, save ->
            currentY -= this.createSave(save, index, currentY) { it == lastAccessed } + SAVE_PADDING
        }
    }

    override fun dispose() {
        super.dispose()


        menuAssetsManager.dispose()
    }

    fun addSave(save: GameSave) {
        val currentY = this.background.children
            .filter { it is Group && it.children.any { it is SaveBackground } }
            .flatMap { (it as Group).children }
            .filterIsInstance<SaveBackground>()
            .foldIndexed(BASE_SAVE_Y) { index, acc, actor ->
                actor.setFocused(false)

                if (this.focusedIndex == index) {
                    actor.y - SAVE_PADDING
                } else {
                    acc
                }
            }

        this.saves.add(save)
        val lastIndex = this.saves.lastIndex
        this.focusedIndex = lastIndex

        this.prefs.flush {
            this["$lastIndex"] = this@SavesSelectionScreen.json.toJson(save)
            this["count"] = lastIndex + 1
        }

        this.createSave(save, lastIndex, currentY) { true }
    }

    private fun createSave(save: GameSave, index: Int, currentY: Float, isFocused: (GameSave) -> Boolean): Float {
        var height by Delegates.notNull<Float>()

        val currentlyFocused = isFocused(save)

        var positionX by Delegates.notNull<Float>()
        var positionY by Delegates.notNull<Float>()

        val group = Group()
        group.addActor(SaveBackground(currentlyFocused).also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f, currentY - it.height)
            it.addListener(SelectOrLoadSave(this, index, it, save))

            height = it.height
            positionX = it.x
            positionY = it.y
        })
        group.addActor(Date(save.creationDate).also {
            it.setPosition(positionX + 150f, positionY + 59f)
        })
        group.addActor(Date(save.lastAccessedDate).also {
            it.setPosition(positionX + 150f, positionY + 37f)
        })
        group.addActor(LevelNumber(save.lastStageCompleted + 1).also {
            it.setPosition(positionX + 380f, positionY + 58f)
        })
        group.addActor(Credits(save.credits).also {
            it.setPosition(positionX + 445f - it.width, positionY + 31f)
        })
        group.addActor(Name(save.name).also {
            it.setPosition(positionX + 86f, positionY + 81f)
        })

        this.background.addActor(group)

        return height
    }

    fun changeFocused(saveIndex: Int) {
        this.focusedIndex = saveIndex

        this.background.children
            .filter { it is Group && it.children.any { it is SaveBackground } }
            .flatMap { (it as Group).children }
            .filterIsInstance<SaveBackground>()
            .forEachIndexed { index, actor -> actor.setFocused(saveIndex == index) }
    }

    @Suppress("GDXKotlinMissingFlush")
    fun removeSaveFromIndex(saveIndex: Int) {
        if (saveIndex < 0 || saveIndex >= this.saves.size)
            return

        this.background.children
            .filter { it is Group && it.children.any { it is SaveBackground } }
            .forEach { it.remove() }

        this.saves.removeAt(saveIndex)

        this.prefs.flush {
            this["count"] = this@SavesSelectionScreen.saves.size

            this.remove("$saveIndex")

            for (index in saveIndex until this@SavesSelectionScreen.saves.size) {
                this["$index"] = this.getString("${index + 1}")
            }
            this.remove("${this@SavesSelectionScreen.saves.size}")
        }

        val newlyFocused = this.saves.maxByOrNull { it.lastAccessedDate }
        this.focusedIndex = newlyFocused?.let { this.saves.indexOf(it) } ?: -1

        var currentY = BASE_SAVE_Y
        this.saves.forEachIndexed { index, save ->
            currentY -= this.createSave(save, index, currentY) { it == newlyFocused } + SAVE_PADDING
        }
    }

    private companion object {
        const val SAVE_PADDING = 10f
        const val BASE_SAVE_Y = UI_HEIGHT * 8f / 10f - 12f
    }
}