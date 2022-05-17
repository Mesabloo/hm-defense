package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable
import ktx.assets.load

class BackgroundAssetsManager : Disposable {
    companion object {
        fun backgroundPath(level: Int, part: Int): String =
            "gfx/terrains/${
                level.toString().padStart(2, '0')
            }/${
                part.toString().padStart(2, '0')
            }.jpg"

        fun backgroundSmallPath(level: Int, part: Int): String =
            "gfx/terrains/${
                level.toString().padStart(2, '0')
            }/${
                part.toString().padStart(2, '0')
            }-small.png"
    }

    fun init() {
        allPaths().forEach { s -> assetManager.load<Texture>(s) }
    }

    fun texture(level: Int, part: Int): Texture = assetManager.get(backgroundPath(level, part))

    fun textureSmall(level: Int, part: Int): Texture = assetManager.get(backgroundSmallPath(level, part))

    private fun allPaths() = (1..27).flatMap { i ->
        sequenceOf(
            backgroundPath(i, 1),
            backgroundSmallPath(i, 1),
            backgroundPath(i, 2),
            backgroundSmallPath(i, 2)
        )
    }

    fun isLoaded() = allPaths().fold(true) { acc, s -> acc && assetManager.isLoaded(s) }

    override fun dispose() {
        allPaths().forEach { s -> assetManager.get<Texture>(s).dispose() }
    }
}

val backgroundAssetsManager = BackgroundAssetsManager()