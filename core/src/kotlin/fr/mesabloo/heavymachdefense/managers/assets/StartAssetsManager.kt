package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import ktx.assets.load

class StartAssetsManager : Disposable {
    companion object {
        const val TITLE_BACKGROUND = "gfx/ui/splash/title.jpg"
        const val TAP_TO_START = "gfx/ui/splash/tap_to_start.png"
    }

    fun preload() {
        assetManager.load<Texture>(TITLE_BACKGROUND)
        assetManager.load<Texture>(TAP_TO_START)
    }

    fun isFullyLoaded(): Boolean =
        listOf(TITLE_BACKGROUND, TAP_TO_START)
            .fold(true) { acc, path ->
                acc && assetManager.isLoaded(path)
            }

    override fun dispose() {
        assetManager.unload(TITLE_BACKGROUND)
        assetManager.unload(TAP_TO_START)
    }

    fun texture(name: String): TextureRegion = TextureRegion(assetManager.get<Texture>(name))
}

val startAssetsManager by lazy { StartAssetsManager() }