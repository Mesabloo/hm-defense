package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.input.processor

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.events.MouseLeftClickEvent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.events.MouseScrollEvent
import ktx.app.KtxInputAdapter

class MouseInputProcessor(private val signal: Signal<MouseInputEvent>): KtxInputAdapter {
    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        signal.dispatch(MouseScrollEvent(amountX, amountY))
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == Input.Buttons.LEFT) {
            // TODO: handle positions
            signal.dispatch(MouseLeftClickEvent(true))
            return false
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == Input.Buttons.LEFT) {
            // TODO: handle positions
            signal.dispatch(MouseLeftClickEvent(false))
            return false
        }
        return true
    }
}