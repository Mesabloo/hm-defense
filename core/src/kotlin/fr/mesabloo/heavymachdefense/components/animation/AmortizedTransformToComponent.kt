package fr.mesabloo.heavymachdefense.components.animation

import ktx.ashley.Mapper
import kotlin.properties.Delegates

class AmortizedTransformToComponent : AbstractAnimationComponent() {
    var destinationX by Delegates.notNull<Float>()
    var destinationY by Delegates.notNull<Float>()

    companion object: Mapper<AmortizedTransformToComponent>()
}