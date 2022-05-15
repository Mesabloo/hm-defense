package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.assets.getValue
import ktx.assets.load

class AssetsManager {
    companion object {
        private val assetManager = AssetManager()

        fun init() {
            assetManager.finishLoading()
        }

        fun textureFromAtlasRegion(atlas: TextureAtlas, regionName: String): TextureRegion =
            atlas.findRegion(regionName)

        /////////// Atlases //////////////

        val machineBodies by assetManager.load<TextureAtlas>("gfx/models/machines/bodies.atlas")
    }
}