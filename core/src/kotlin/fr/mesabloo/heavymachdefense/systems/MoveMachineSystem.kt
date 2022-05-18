package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import ktx.ashley.allOf
import ktx.ashley.get

class MoveMachineSystem : IteratingSystem(
    allOf(
        MachineComponent::class
    ).get()
) {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            val machineComponent = entity[MachineComponent.mapper]!!

            // For now, constant low velocity towards the right

            //machineComponent.body.setTransform(0f, 0f, 90f * MathUtils.degreesToRadians)
            machineComponent.body.setLinearVelocity(4f, 0f)
        }
    }
}