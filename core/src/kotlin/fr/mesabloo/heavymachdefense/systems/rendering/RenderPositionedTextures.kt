package fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.has

class RenderPositionedTextures(private val batch: SpriteBatch) : IteratingSystem(
    allOf(
        TextureComponent::class,
        PositionComponent::class
    ).get()
) {
    private var renderingQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val renderingQueue =
            this.renderingQueue.toMutableList()
                .filter { it.has(PositionComponent.mapper) }
                .sortedBy { it[PositionComponent.mapper]!!.z }

        //this.batch.enableBlending()
        this.batch.begin()

        for (entity in renderingQueue) {
            val positionComponent = entity[PositionComponent.mapper]!!
            val textureComponent = entity[TextureComponent.mapper]!!

            val texture = textureComponent.texture!!
            val sprite = Sprite(texture)

            val newWidth = textureComponent.width * textureComponent.scaleX
            val newHeight = textureComponent.height * textureComponent.scaleY

            val adaptFromScaleX = (textureComponent.width - newWidth) / 2f
            val adaptFromScaleY = (textureComponent.height - newHeight) / 2f

            //sprite.setOrigin(sprite.width / 2, sprite.height / 2)
            sprite.setOriginCenter()
            //sprite.setScale(textureComponent.scaleX, textureComponent.scaleY)
            sprite.setSize(newWidth, newHeight)
            sprite.setPosition(positionComponent.x + adaptFromScaleX, positionComponent.y + adaptFromScaleY)
            sprite.setAlpha(textureComponent.opacity)

            sprite.draw(this.batch)
        }

        this.batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        this.renderingQueue.add(entity)
    }
}