package fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import ktx.assets.load

class BackgroundMusicManager : Disposable {
    companion object {
        const val INTRO = "sfx/misc/intro.mp3"
        const val GAMEPLAY = "sfx/misc/gameplay.mp3"
    }

    private val manager = AssetManager()

    fun preload() {
        this.manager.load<Music>(INTRO)
        this.manager.load<Music>(GAMEPLAY)
    }

    fun update() {
        this.manager.update()
    }

    fun isFullyLoaded() = this.manager.isLoaded(INTRO) && this.manager.isLoaded(GAMEPLAY)

    fun load(path: String) : Music = this.manager.get(path)

    override fun dispose() {
        this.manager.dispose()
    }
}

val backgroundMusicManager by lazy { BackgroundMusicManager() }

class SoundEffectManager : Disposable {
    override fun dispose() {
        TODO("Not yet implemented")
    }
}