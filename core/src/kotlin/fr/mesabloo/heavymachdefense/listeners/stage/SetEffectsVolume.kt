package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import kotlin.reflect.KMutableProperty0

class SetEffectsVolume(private val effectsVolume: KMutableProperty0<Float>) : ChangeListener() {
    override fun changed(event: ChangeEvent?, actor: Actor?) {
        effectsVolume.set((actor as Slider).value)
    }
}