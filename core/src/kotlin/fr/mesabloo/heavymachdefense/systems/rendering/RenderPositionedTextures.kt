package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.TextureComponent
import ktx.ashley.allOf
import ktx.ashley.get

class RenderPositionedTextures(private val batch: SpriteBatch) : IteratingSystem(
    allOf(
        TextureComponent::class,
        PositionComponent::class
    ).get()
) {
    private var renderingQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val renderingQueue = this.renderingQueue.toMutableList()
        renderingQueue.sortWith { o1, o2 -> o1[PositionComponent.mapper]!!.z.compareTo(o2[PositionComponent.mapper]!!.z) }

        for (entity in renderingQueue) {
            val positionComponent = entity[PositionComponent.mapper]!!
            val textureComponent = entity[TextureComponent.mapper]!!

            val texture = textureComponent.texture!!
            val sprite = Sprite(texture)

            this.batch.draw(
                sprite,
                positionComponent.x,
                positionComponent.y
            )
        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            this.renderingQueue.add(entity)
        }
    }
}