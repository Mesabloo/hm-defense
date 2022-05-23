package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import ktx.assets.load

class LevelSelectionAssetsManager : Disposable {
    companion object {
        fun smallBackground(level: Int, part: Int): String =
            "gfx/terrains/${
                level.toString().padStart(2, '0')
            }/${
                part.toString().padStart(2, '0')
            }-small.png"

        const val SELECT_BUTTONS: String = "gfx/ui/buttons/stage-select.atlas"
        const val BACKGROUND: String = "gfx/ui/level/background.jpg"
        const val FOREGROUND: String = "gfx/ui/level/foreground.png"

        enum class ButtonState {
            DISABLED, NORMAL, HOVERED, SELECTED
        }
    }

    private fun allBackgroundPaths() = (1..27).flatMap { i -> sequenceOf(smallBackground(i, 1), smallBackground(i, 2)) }

    fun preload() {
        this.allBackgroundPaths().forEach { assetManager.load<Texture>(it) }
        assetManager.load<TextureAtlas>(SELECT_BUTTONS)
        assetManager.load<Texture>(BACKGROUND)
        assetManager.load<Texture>(FOREGROUND)
    }

    fun texture(path: String): TextureRegion = TextureRegion(assetManager.get<Texture>(path))

    fun stageBackground(level: Int): Pair<TextureRegion, TextureRegion> = Pair(
        TextureRegion(
            assetManager.get<Texture>(smallBackground(level, 1))
        ), TextureRegion(
            assetManager.get<Texture>(smallBackground(level, 2))
        )
    )

    fun button(state: ButtonState): TextureRegion =
        assetManager.get<TextureAtlas>(SELECT_BUTTONS)
            .findRegion(
                when (state) {
                    ButtonState.DISABLED -> "disabled"
                    ButtonState.NORMAL -> "normal"
                    ButtonState.HOVERED -> "hovered"
                    ButtonState.SELECTED -> "selected"
                }
            )

    fun isFullyLoaded(): Boolean =
        this.allBackgroundPaths().fold(true) { acc, path -> acc && assetManager.isLoaded(path) }
                && assetManager.isLoaded(SELECT_BUTTONS)

    override fun dispose() {
        this.allBackgroundPaths().forEach { assetManager.unload(it) }
        assetManager.unload(SELECT_BUTTONS)
    }
}

val levelSelectionAssetsManager by lazy { LevelSelectionAssetsManager() }