package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineSpriteComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.AssetsManager
import ktx.ashley.allOf
import ktx.ashley.get

class RenderMachinesSystem(private val batch: SpriteBatch) : IteratingSystem(
    allOf(
        MachineSpriteComponent::class,
        PositionComponent::class
    ).get()
) {
    private var renderingQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val renderingQueue =
            this.renderingQueue.toSortedSet { o1, o2 -> o1[PositionComponent.mapper]!!.z.compareTo(o2[PositionComponent.mapper]!!.z) }

//        this.batch.begin()
        this.batch.disableBlending()

        for (entity in renderingQueue) {
            val positionComponent = entity[PositionComponent.mapper]!!
            val machineSpriteComponent = entity[MachineSpriteComponent.mapper]!!

            val regionName =
                AssetsManager.machineTileName(machineSpriteComponent.kind.machineName, machineSpriteComponent.level)
            val bodySprite = Sprite(AssetsManager.textureFromAtlasRegion(AssetsManager.machineBodies, regionName))

            this.batch.draw(
                bodySprite,
                positionComponent.x - positionComponent.width / 2,
                positionComponent.y - positionComponent.height / 2
            )
        }

//        this.batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            this.renderingQueue.add(entity)
        }
    }
}