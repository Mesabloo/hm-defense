package fr.mesabloo.heavymachdefense.components.input

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import ktx.ashley.Mapper
import ktx.math.vec2

class MouseInputComponent : Component {
    var leftClick: Boolean = false
    var rightClick: Boolean = false
    var scrollX: Float = 0f
    var scrollY: Float = 0f
    var clickPosition: Vector2 = vec2(0f, 0f)

    companion object: Mapper<MouseInputComponent>()
}