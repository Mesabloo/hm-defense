package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.ashley.Mapper

class CameraComponent : Component {
    lateinit var camera: OrthographicCamera

    companion object: Mapper<CameraComponent>()
}