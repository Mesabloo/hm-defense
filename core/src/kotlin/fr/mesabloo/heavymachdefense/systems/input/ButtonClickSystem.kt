package fr.mesabloo.heavymachdefense.systems.input

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.components.ui.ButtonClickComponent
import fr.mesabloo.heavymachdefense.components.ui.OnClickListener
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import ktx.ashley.allOf
import ktx.ashley.get

class ButtonClickSystem : IteratingSystem(
    allOf(
        TextureComponent::class,
        PositionComponent::class,
        MouseInputComponent::class,
        OnClickListener::class,
        ButtonClickComponent::class
    ).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val textureComponent = entity[TextureComponent.mapper]!!
        val positionComponent = entity[PositionComponent.mapper]!!
        val mouseInputComponent = entity[MouseInputComponent.mapper]!!
        val onClickListener = entity[OnClickListener.mapper]!!
        val buttonClickComponent = entity[ButtonClickComponent.mapper]!!

        val width = textureComponent.width
        val height = textureComponent.height
        val x = positionComponent.x
        val y = positionComponent.y

        var clickPosition = Vector2(mouseInputComponent.clickPosition)
        clickPosition = onClickListener.viewport.unproject(clickPosition)

        if (mouseInputComponent.leftClick) {
            if (!buttonClickComponent.alreadyClicked
                && clickPosition.x >= x && clickPosition.x <= x + width
                && clickPosition.y >= y && clickPosition.y <= y + height
            ) {
                onClickListener.listener()

                buttonClickComponent.alreadyClicked = true
            }
        } else {
            buttonClickComponent.alreadyClicked = false
        }
        textureComponent.texture = buttonAssetsManager.texture(buttonClickComponent.buttonKind, buttonClickComponent.alreadyClicked)
    }
}