package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
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

        fun machineTileName(machineName: String, level: Long): String =
            "${machineName}-${level.toString().padStart(2, '0')}"

        fun background(number: Int, part: Int): Texture =
            assetManager.get(
                "gfx/terrains/${number.toString().padStart(2, '0')}/${
                    part.toString().padStart(2, '0')
                }.jpg"
            )

        /////////// Atlases //////////////

        val machineBodies by assetManager.load<TextureAtlas>("gfx/models/machines/bodies.atlas")
        val machineWeapons by assetManager.load<TextureAtlas>("gfx/models/machines/weapons.atlas")

        /////////// Backgrounds //////////

        private val bg0101 by assetManager.load<Texture>("gfx/terrains/01/01.jpg")
        private val bg0102 by assetManager.load<Texture>("gfx/terrains/01/02.jpg")
    }
}