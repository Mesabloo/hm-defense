package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

/**
 * Holds state for UI buttons.
 *
 * Mainly to keep track of whether it has already been clicked or not.
 */
class ButtonClickComponent : Component {
    /**
     * Is the button being clicked?
     */
    var alreadyClicked: Boolean = false

    /**
     * The kind of [Button], if a common UI button, else `null`.
     * This property is used to dynamically change some UI button textures to the “selected” alternatives.
     */
    var buttonKind: Button? = null

    companion object: Mapper<ButtonClickComponent>()
}