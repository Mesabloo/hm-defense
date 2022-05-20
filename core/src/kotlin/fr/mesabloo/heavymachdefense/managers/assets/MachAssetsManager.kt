package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import ktx.assets.getValue
import ktx.assets.load

private const val MACHINE_BODIES_PATH: String = "gfx/models/machines/bodies.atlas"
private const val MACHINE_WEAPONS_PATH: String = "gfx/models/machines/weapons.atlas"

class MachAssetsManager : Disposable {
    companion object {
        fun textureFromAtlasRegion(atlas: TextureAtlas, regionName: String): TextureRegion =
            atlas.findRegion(regionName)
    }

    fun machineTileName(machineName: String, level: Long): String =
        "${machineName}-${level.toString().padStart(2, '0')}"

    fun isLoaded() =
        assetManager.isLoaded(MACHINE_BODIES_PATH)
                && assetManager.isLoaded(MACHINE_WEAPONS_PATH)

    override fun dispose() {
        this.machineBodies.dispose()
        this.machineWeapons.dispose()
    }

    /////////// INTERNAL /////////////

    val machineBodies by assetManager.load<TextureAtlas>(MACHINE_BODIES_PATH)
    val machineWeapons by assetManager.load<TextureAtlas>(MACHINE_WEAPONS_PATH)
}

val machAssetsManager by lazy { MachAssetsManager() }