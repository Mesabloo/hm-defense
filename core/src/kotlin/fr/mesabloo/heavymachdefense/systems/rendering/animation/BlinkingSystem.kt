package fr.mesabloo.heavymachdefense.systems.rendering.animation

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.animation.BlinkingComponent
import ktx.ashley.allOf
import ktx.ashley.get
import java.util.*

class BlinkingSystem : IteratingSystem(allOf(TextureComponent::class, BlinkingComponent::class).get()) {
    private var lastUpdate: IdentityHashMap<Entity, Float> = IdentityHashMap()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (lastUpdate[entity] == null) {
            lastUpdate[entity] = 0f
        }

        val blinkingComponent = entity[BlinkingComponent.mapper]!!
        val textureComponent = entity[TextureComponent.mapper]!!

        val freqHz = blinkingComponent.frequency / 1000f
        val timeInterval = 1f / freqHz

        var currentTime = this.lastUpdate[entity]!!

        if (currentTime > timeInterval) {
            textureComponent.opacity = if (textureComponent.opacity == 1f) 0f else 1f

            currentTime -= timeInterval
        }
        currentTime += deltaTime * 1000

        this.lastUpdate[entity] = currentTime
    }
}