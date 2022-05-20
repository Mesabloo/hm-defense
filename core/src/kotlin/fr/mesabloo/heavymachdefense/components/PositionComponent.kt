package fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class PositionComponent(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f,
) : Component {
    companion object : Mapper<PositionComponent>()
}