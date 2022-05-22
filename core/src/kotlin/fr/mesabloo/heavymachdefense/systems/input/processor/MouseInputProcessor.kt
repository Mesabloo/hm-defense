package fr.mesabloo.heavymachdefense.systems.input.processor

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import com.badlogic.gdx.input.GestureDetector.GestureAdapter
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.events.MouseLeftClickEvent
import fr.mesabloo.heavymachdefense.events.MouseScrollEvent
import ktx.app.KtxInputAdapter
import ktx.math.vec2

private const val SCROLL_MULTIPLIER: Float = 1250.0f

class MouseInputProcessor(private val signal: Signal<MouseInputEvent>) : KtxInputAdapter, GestureAdapter() {
    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        signal.dispatch(MouseScrollEvent(amountX * SCROLL_MULTIPLIER, amountY * SCROLL_MULTIPLIER))
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

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean =
        when (button) {
            Input.Buttons.LEFT -> {
                signal.dispatch(MouseScrollEvent(velocityX, velocityY))
                false
            }
            else -> true
        }
}