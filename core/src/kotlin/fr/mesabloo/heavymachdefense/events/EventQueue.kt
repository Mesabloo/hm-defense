package fr.mesabloo.heavymachdefense.events

import com.badlogic.ashley.signals.Listener
import com.badlogic.ashley.signals.Signal
import java.util.*

class EventQueue<T: GameEvent> : Listener<T> {
    private val queue = PriorityQueue<T>()

    fun toList(): List<T> {
        val evts = this.queue.toList()
        this.queue.clear()
        return evts
    }

    fun poll(): T = this.queue.poll()

    override fun receive(signal: Signal<T>?, `object`: T) {
        queue.add(`object`)
    }
}