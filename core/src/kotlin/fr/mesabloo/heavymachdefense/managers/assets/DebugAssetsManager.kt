package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable
import fr.mesabloo.heavymachdefense.DEBUG
import ktx.assets.load


class DebugAssetsManager : Disposable {
    companion object {
        const val BLACK_BG = "gfx/ui/debug/black.9.png"

        const val FONT: String = "cone_14"
    }

    lateinit var font: BitmapFont
        private set

    fun preload() {
        assetManager.load<Texture>(BLACK_BG)

        this.font = BitmapFont(Gdx.files.internal("fonts/$FONT.fnt")).also {
            it.setUseIntegerPositions(false)
        }
    }

    fun isFullyLoaded() = if (DEBUG) assetManager.isLoaded(BLACK_BG) else true

    fun get(path: String): Texture = assetManager.get(path)

    override fun dispose() {
        assetManager.unload(BLACK_BG)
    }
}

val debugAssetsManager by lazy { DebugAssetsManager() }