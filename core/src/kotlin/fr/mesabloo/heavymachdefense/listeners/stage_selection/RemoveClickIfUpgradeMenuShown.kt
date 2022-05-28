package fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import kotlin.reflect.KProperty0

class RemoveClickIfUpgradeMenuShown(private val upgradeMenuShown: KProperty0<Boolean>) : ClickListener() {
    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        !this.upgradeMenuShown.get().also {
            if (it) event.cancel()
        }
}