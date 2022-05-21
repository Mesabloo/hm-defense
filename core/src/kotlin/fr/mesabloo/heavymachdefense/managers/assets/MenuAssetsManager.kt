package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import ktx.assets.load

class MenuAssetsManager: Disposable {
    companion object {
        const val BACKGROUND = "gfx/ui/slots/background.jpg"
        const val FOREGROUND = "gfx/ui/slots/foreground.png"
    }

    fun preload() {
        assetManager.load<Texture>(BACKGROUND)
        assetManager.load<Texture>(FOREGROUND)
    }

    fun get(path: String): TextureRegion = TextureRegion(assetManager.get<Texture>(path))

    fun isFullyLoaded(): Boolean =
        listOf(BACKGROUND, FOREGROUND)
            .fold(true) { acc, path -> acc && assetManager.isLoaded(path) }

    override fun dispose() {
        assetManager.unload(BACKGROUND)
        assetManager.unload(FOREGROUND)
    }
}

val menuAssetsManager by lazy { MenuAssetsManager() }