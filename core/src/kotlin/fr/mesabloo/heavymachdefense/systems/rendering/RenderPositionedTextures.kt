package fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
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

        val renderingQueue = this.renderingQueue.toMutableList().sortedBy { e -> e[PositionComponent.mapper]!!.z }

        this.batch.enableBlending()
        this.batch.begin()

        for (entity in renderingQueue) {
            val positionComponent = entity[PositionComponent.mapper]!!
            val textureComponent = entity[TextureComponent.mapper]!!

            val texture = textureComponent.texture!!
            val sprite = Sprite(texture)

            //sprite.setOrigin(sprite.width / 2, sprite.height / 2)
            sprite.setOriginCenter()
            sprite.setSize(textureComponent.width, textureComponent.height)
            sprite.setPosition(positionComponent.x, positionComponent.y)
            sprite.setAlpha(textureComponent.opacity)

            sprite.draw(this.batch)
        }

        this.batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            this.renderingQueue.add(entity)
        }
    }
}