package fr.mesabloo.heavymachdefense.components.animation

import ktx.ashley.Mapper
import kotlin.properties.Delegates

/**
 * A simple smooth scaling animation.
 */
class ScaleComponent : AbstractAnimationComponent() {
    /**
     * How much to scale on the X axis at the end of the animation.
     */
    var destinationX by Delegates.notNull<Float>()

    /**
     * How much to scale on the Y axis at the end of the animation.
     */
    var destinationY by Delegates.notNull<Float>()

    companion object: Mapper<ScaleComponent>()
}