package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.BodyComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import ktx.ashley.allOf
import ktx.ashley.get

class UpdatePositionFromBodySystem : IteratingSystem(allOf(BodyComponent::class, PositionComponent::class).get()) {
    @Suppress("NAME_SHADOWING")
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val entity = entity!!

        val positionComponent = entity[PositionComponent.mapper]!!
        val bodyComponent = entity[BodyComponent.mapper]!!

        positionComponent.x = bodyComponent.body.position.x
        positionComponent.y = bodyComponent.body.position.y
    }
}