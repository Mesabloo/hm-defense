package fr.mesabloo.heavymachdefense.components.animation

import com.badlogic.ashley.core.Component
import kotlin.properties.Delegates

/**
 * Contains common animation information such as delay and duration.
 */
abstract class AbstractAnimationComponent : Component {
    /**
     * How much to wait for before starting the animation, in seconds.
     */
    var delay: Float = 0f

    /**
     * How long does the animation last for, is seconds.
     */
    var duration by Delegates.notNull<Float>()
}