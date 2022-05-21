package fr.mesabloo.heavymachdefense.components.animation

import com.badlogic.ashley.core.Component
import kotlin.properties.Delegates

abstract class AbstractAnimationComponent : Component {
    var delay: Float = 0f
    var duration by Delegates.notNull<Float>()
}