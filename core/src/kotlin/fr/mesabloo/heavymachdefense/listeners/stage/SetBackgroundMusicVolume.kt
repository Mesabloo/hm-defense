package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import kotlin.reflect.KMutableProperty0

class SetBackgroundMusicVolume(private val backgroundMusicVolume: KMutableProperty0<Float>) : ChangeListener() {
    override fun changed(event: ChangeEvent, actor: Actor) {
        backgroundMusicVolume.set((actor as Slider).value)
    }
}