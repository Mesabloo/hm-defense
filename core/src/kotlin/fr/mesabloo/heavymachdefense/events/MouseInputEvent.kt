package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.events

interface MouseInputEvent: GameEvent, Comparable<MouseInputEvent>

data class MouseScrollEvent(val x: Float, val y: Float): MouseInputEvent {
    override fun compareTo(other: MouseInputEvent): Int = 0
}

data class MouseLeftClickEvent(val pressed: Boolean): MouseInputEvent {
    override fun compareTo(other: MouseInputEvent): Int = 0
}
