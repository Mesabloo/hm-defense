package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class RotationComponent: Component {
    var angle: Float = 0f // in degrees

    companion object: Mapper<RotationComponent>()
}