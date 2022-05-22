package fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.internal.Batcher
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.hasNot

class RenderPositionedTextures(private val batcher: Batcher) : IteratingSystem(
    allOf(
        TextureComponent::class,
        PositionComponent::class
    ).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (entity.hasNot(PositionComponent.mapper) || entity.hasNot(TextureComponent.mapper)) {
            return
        }

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

        this.batcher.draw(sprite, positionComponent.zIndex)
    }
}