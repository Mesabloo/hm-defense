package fr.mesabloo.heavymachdefense.systems.input.processor

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.events.MouseLeftClickEvent
import fr.mesabloo.heavymachdefense.events.MouseScrollEvent
import ktx.app.KtxInputAdapter
import ktx.math.vec2

class MouseInputProcessor(private val signal: Signal<MouseInputEvent>) : KtxInputAdapter {
    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        signal.dispatch(MouseScrollEvent(amountX, amountY))
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean =
        when (pointer) {
            Input.Buttons.LEFT -> {
                signal.dispatch(MouseLeftClickEvent(true, vec2(screenX.toFloat(), screenY.toFloat())))
                false
            }
            else -> true
        }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean =
        when (pointer) {
            Input.Buttons.LEFT -> {
                signal.dispatch(MouseLeftClickEvent(false, vec2(screenX.toFloat(), screenY.toFloat())))
                false
            }
            else -> true
        }
}