package fr.mesabloo.heavymachdefense.components.animation

import ktx.ashley.Mapper
import kotlin.properties.Delegates

/**
 * A smooth transformation animation.
 */
class AmortizedTransformToComponent : AbstractAnimationComponent() {
    /**
     * The world-local X coordinate to reach at the end of the animation.
     */
    var destinationX by Delegates.notNull<Float>()

    /**
     * The world-local Y coordinate to reach at the end of the animation.
     */
    var destinationY by Delegates.notNull<Float>()

    companion object: Mapper<AmortizedTransformToComponent>()
}
