package fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper
import kotlin.properties.Delegates

class BlinkingComponent : Component {
    /**
     * The number of times the component should appear/disappear in one second
     */
    var frequency by Delegates.notNull<Float>()

    companion object: Mapper<BlinkingComponent>()
}