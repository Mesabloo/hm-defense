package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.utils.Disposable

class MenuAssetsManager: Disposable {
    fun isFullyLoaded(): Boolean {
        return false
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}

val menuAssetsManager by lazy { MenuAssetsManager() }