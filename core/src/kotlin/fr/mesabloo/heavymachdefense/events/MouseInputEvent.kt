package fr.mesabloo.heavymachdefense.events

import com.badlogic.gdx.math.Vector2

interface MouseInputEvent: GameEvent, Comparable<MouseInputEvent>

data class MouseScrollEvent(val x: Float, val y: Float): MouseInputEvent {
    override fun compareTo(other: MouseInputEvent): Int = 0
}

data class MouseLeftClickEvent(val pressed: Boolean, val position: Vector2): MouseInputEvent {
    override fun compareTo(other: MouseInputEvent): Int = 0
}
