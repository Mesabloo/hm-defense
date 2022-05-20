package fr.mesabloo.heavymachdefense.systems.input

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.events.EventQueue
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.events.MouseLeftClickEvent
import fr.mesabloo.heavymachdefense.events.MouseScrollEvent
import ktx.ashley.allOf
import ktx.ashley.get

class MouseInputSystem(private val signal: Signal<MouseInputEvent>) :
    IteratingSystem(allOf(MouseInputComponent::class).get()) {
    private lateinit var mouseEvents: List<MouseInputEvent>
    private var eventQueue: EventQueue<MouseInputEvent> = EventQueue()

    init {
        signal.add(this.eventQueue)
    }

    override fun update(deltaTime: Float) {
        this.mouseEvents = this.eventQueue.toList()

        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            val mouseInputComponent = entity[MouseInputComponent.mapper]!!

            for (ev: MouseInputEvent in this.mouseEvents) {
                when (ev) {
                    is MouseScrollEvent -> {
                        mouseInputComponent.scrollX = ev.x
                        mouseInputComponent.scrollY = ev.y
                    }
                    is MouseLeftClickEvent -> {
                        mouseInputComponent.leftClick = ev.pressed
                    }
                }
            }
        }
    }
}