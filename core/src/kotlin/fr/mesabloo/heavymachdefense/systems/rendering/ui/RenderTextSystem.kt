package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering.ui

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.ui.TextComponent
import ktx.ashley.allOf
import ktx.ashley.get

class RenderTextSystem(private val batch: SpriteBatch) :
    IteratingSystem(allOf(TextComponent::class, PositionComponent::class).get()) {
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            val textComponent = entity[TextComponent.mapper]!!
            val positionComponent = entity[PositionComponent.mapper]!!

            this.batch.begin()
            textComponent.font.draw(this.batch, textComponent.message, positionComponent.x, positionComponent.y)
            this.batch.end()
        }
    }
}