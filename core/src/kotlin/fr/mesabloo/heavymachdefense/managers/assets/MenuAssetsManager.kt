package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import ktx.assets.load

class MenuAssetsManager: Disposable {
    companion object {
        const val BACKGROUND = "gfx/ui/slots/background.jpg"

        const val SLOT = "gfx/ui/slots/slots.atlas"
    }

    fun preload() {
        assetManager.load<Texture>(BACKGROUND)

        assetManager.load<TextureAtlas>(SLOT)
    }

    fun getSlot(isSelected: Boolean): TextureRegion =
        assetManager.get<TextureAtlas>(SLOT)
            .findRegion("slot${if (isSelected) "-selected" else ""}")

    fun get(path: String): TextureRegion = TextureRegion(assetManager.get<Texture>(path))

    fun isFullyLoaded(): Boolean =
        listOf(BACKGROUND, SLOT)
            .fold(true) { acc, path -> acc && assetManager.isLoaded(path) }

    override fun dispose() {
        assetManager.unload(BACKGROUND)
        assetManager.unload(SLOT)
    }
}

val menuAssetsManager by lazy { MenuAssetsManager() }