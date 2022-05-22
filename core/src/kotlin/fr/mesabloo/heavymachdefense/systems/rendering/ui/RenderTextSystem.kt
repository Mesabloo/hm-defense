package fr.mesabloo.heavymachdefense.systems.rendering.ui

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.ui.TextComponent
import fr.mesabloo.heavymachdefense.components.ui.TextComponent.Companion.CENTER_X
import fr.mesabloo.heavymachdefense.components.ui.TextComponent.Companion.CENTER_Y
import fr.mesabloo.heavymachdefense.components.ui.TextComponent.Companion.RIGHT_X
import fr.mesabloo.heavymachdefense.components.ui.TextComponent.Companion.TOP_Y
import fr.mesabloo.heavymachdefense.internal.Batcher
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.math.vec2

class RenderTextSystem(private val batcher: Batcher) :
    IteratingSystem(allOf(TextComponent::class, PositionComponent::class).get()) {

    private val glyphLayout = GlyphLayout()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val textComponent = entity[TextComponent.mapper]!!
        val positionComponent = entity[PositionComponent.mapper]!!

        this.glyphLayout.setText(textComponent.font, textComponent.message)
        val size = vec2(this.glyphLayout.width, this.glyphLayout.height)

        val align = textComponent.align
        val alignmentX = if ((align and RIGHT_X) == 0b1L) {
            -size.x
        } else if ((align and CENTER_X) == 0b1L) {
            -size.x / 2f
        } else {
            0f
        }
        val alignmentY = if ((align and TOP_Y) == 0b1L) {
            -size.y
        } else if ((align and CENTER_Y) == 0b1L) {
            -size.y / 2f
        } else {
            0f
        }

        this.batcher.draw(
            textComponent.font,
            textComponent.message,
            vec2(
                positionComponent.x + alignmentX,
                positionComponent.y + alignmentY
            ),
            positionComponent.zIndex
        )
    }
}