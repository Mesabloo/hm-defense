package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getValue
import ktx.assets.loadOnDemand

class AssetsManager {
    companion object {
        private val assetManager = AssetManager()

        fun init(): Unit {

        }

        val rifleBody01 by assetManager.loadOnDemand<Texture>("gfx/models/machines/rifle/body-01.png")
    }
}