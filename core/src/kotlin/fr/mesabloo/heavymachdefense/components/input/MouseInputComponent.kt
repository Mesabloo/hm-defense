package fr.mesabloo.heavymachdefense.components.input

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class MouseInputComponent : Component {
    var leftClick: Boolean = false
    var rightClick: Boolean = false
    var scrollX: Float = 0f
    var scrollY: Float = 0f

    companion object: Mapper<MouseInputComponent>()
}