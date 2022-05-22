package fr.mesabloo.heavymachdefense.events

import com.badlogic.gdx.math.Vector2

/**
 * The base interface for all mouse input events (generated from [com.badlogic.gdx.InputProcessor]s).
 */
interface MouseInputEvent: GameEvent, Comparable<MouseInputEvent>

/**
 * The scroll wheel has been moved on the X and/or Y axes, or a finger has been dragged (flinged) in the stored direction.
 *
 * @param x The direction on the X axis
 * @param y The direction on the Y axis
 */
data class MouseScrollEvent(val x: Float, val y: Float): MouseInputEvent {
    override fun compareTo(other: MouseInputEvent): Int = 0
}

/**
 * A finger has touched the screen or the left button of the mouse has been pressed.
 *
 * @param pressed Has the finger touched or released the screen (or has the mouse button been pressed or released)?
 * @param position The position of the left click
 */
data class MouseLeftClickEvent(val pressed: Boolean, val position: Vector2): MouseInputEvent {
    override fun compareTo(other: MouseInputEvent): Int = 0
}
