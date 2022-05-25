package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import fr.mesabloo.heavymachdefense.ui.Button
import ktx.assets.load

class ButtonAssetsManager : Disposable {
    companion object {
        const val BUTTONS = "gfx/ui/buttons/common.atlas"
    }

    fun preload() {
        assetManager.load<TextureAtlas>(BUTTONS)
    }

    fun texture(button: Button, isSelected: Boolean): TextureRegion =
        assetManager.get<TextureAtlas>(BUTTONS)
            .findRegion("${button.str}${if (isSelected) "-selected" else ""}")

    fun unsafeTexture(path: String): TextureRegion =
        assetManager.get<TextureAtlas>(BUTTONS)
            .findRegion(path)

    fun isFullyLoaded() = assetManager.isLoaded(BUTTONS)

    override fun dispose() {
        assetManager.unload(BUTTONS)
    }
}

val buttonAssetsManager by lazy { ButtonAssetsManager() }