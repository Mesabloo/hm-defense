package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.Box2DWorldComponent
import ktx.ashley.allOf

class Box2DWorldUpdater(private val world: World): IteratingSystem(allOf(Box2DWorldComponent::class).get()) {
    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        this.world.step(deltaTime, 2, 5)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {

    }
}