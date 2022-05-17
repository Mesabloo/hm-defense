package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.BodyComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineSpriteComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.MachAssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.machAssetsManager
import ktx.ashley.allOf
import ktx.ashley.get

class RenderMachinesSystem(private val batch: SpriteBatch) : IteratingSystem(
    allOf(
        MachineSpriteComponent::class,
        BodyComponent::class
    ).get()
) {
    private var renderingQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val renderingQueue = this.renderingQueue.toMutableList()
        renderingQueue.sortWith { o1, o2 -> o1[PositionComponent.mapper]!!.z.compareTo(o2[PositionComponent.mapper]!!.z) }

        this.batch.begin()

        for (entity in renderingQueue) {
            val bodyComponent = entity[BodyComponent.mapper]!!
            val machineSpriteComponent = entity[MachineSpriteComponent.mapper]!!

            val regionName =
                machAssetsManager.machineTileName(machineSpriteComponent.kind.machineName, machineSpriteComponent.level)
            val bodySprite = Sprite(MachAssetsManager.textureFromAtlasRegion(machAssetsManager.machineBodies, regionName))

            this.batch.draw(
                bodySprite,
                bodyComponent.body.position.x,
                bodyComponent.body.position.y
            )
        }

        this.batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            this.renderingQueue.add(entity)
        }
    }
}