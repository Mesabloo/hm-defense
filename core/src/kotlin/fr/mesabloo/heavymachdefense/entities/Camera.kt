package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.CameraComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import ktx.ashley.entity
import ktx.ashley.with

fun createCamera(engine: Engine, camera: OrthographicCamera): Entity = engine.entity {
    with<CameraComponent> {
        this.camera = camera
    }
    with<MouseInputComponent> {

    }
}