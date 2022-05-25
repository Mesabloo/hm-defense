package fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.ashley.entity

/**
 * A factory method to create an [Entity] for a [com.badlogic.gdx.graphics.Camera] handling mouse input.
 *
 * @param engine The [Engine] to which to add the created [Entity]
 * @param camera The [com.badlogic.gdx.graphics.Camera]
 *
 * @return The [Entity] created for the given [com.badlogic.gdx.graphics.Camera]
 */
fun createCamera(engine: Engine, camera: OrthographicCamera): Entity = engine.entity {
//    with<CameraComponent> {
//        this.camera = camera
//    }
//    with<MouseInputComponent> {
//
//    }
}