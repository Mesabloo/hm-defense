package fr.mesabloo.heavymachdefense.systems.camera

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.components.CameraComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.world.WORLD_HEIGHT
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.abs

private const val SCROLL_MULTIPLIER: Float = 1250.0f

// This needs to be negative
private const val FRICTION = -0.40f

class MoveCameraSystem : IteratingSystem(allOf(CameraComponent::class, MouseInputComponent::class).get()) {
    private var currentlyScrolling: Float = 0f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val cameraComponent = entity[CameraComponent.mapper]!!
        val mouseInputComponent = entity[MouseInputComponent.mapper]!!

        val camera = cameraComponent.camera

        // TODO: use Tween here?

        if (abs(mouseInputComponent.scrollY) >= 0.00001) {
            this.currentlyScrolling = mouseInputComponent.scrollY
            mouseInputComponent.scrollY = 0f
        }

        if (abs(this.currentlyScrolling) >= 0.1) {
            // NOTE: See https://gamedev.stackexchange.com/a/128317 for why the formula
            camera.position.add(0f, (-this.currentlyScrolling * SCROLL_MULTIPLIER) * deltaTime + 1 / 2 * FRICTION + deltaTime * deltaTime, 0f)

            if (camera.position.y < WORLD_HEIGHT / 2) camera.position.set(
                camera.position.x,
                WORLD_HEIGHT / 2,
                camera.position.z
            )
            if (camera.position.y > WORLD_HEIGHT * 3 / 2) camera.position.set(
                camera.position.x,
                WORLD_HEIGHT * 3 / 2,
                camera.position.z
            )

            this.currentlyScrolling /= 1.12f

            camera.update()
        } else {
            this.currentlyScrolling = 0f
        }
    }
}