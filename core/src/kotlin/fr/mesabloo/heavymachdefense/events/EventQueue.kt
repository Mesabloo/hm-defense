package fr.mesabloo.heavymachdefense.events

import com.badlogic.ashley.signals.Listener
import com.badlogic.ashley.signals.Signal
import java.util.*

/**
 * A priority queue which is capable of receiving [GameEvent]s of any kind.
 *
 * @param T The type of [GameEvent] this queue stores.
 */
class EventQueue<T: GameEvent> : Listener<T> {
    private val queue = PriorityQueue<T>()

    /**
     * Fetches all the pending events (in priority order) and clears the queue.
     *
     * @return The queue (as a list) in priority order
     */
    fun toList(): List<T> {
        val evts = this.queue.toList()
        this.queue.clear()
        return evts
    }

    /**
     * Fetch the next event in the queue, or `null` if it is empty.
     *
     * @return The head of the queue or `null` if the queue is empty
     */
    fun poll(): T? = this.queue.poll()

    override fun receive(signal: Signal<T>?, `object`: T) {
        queue.add(`object`)
    }
}