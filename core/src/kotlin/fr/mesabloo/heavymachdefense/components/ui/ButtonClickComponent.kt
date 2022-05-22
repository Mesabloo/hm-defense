package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class ButtonClickComponent : Component {
    var alreadyClicked: Boolean = false
    var buttonKind: Button? = null // keep to null if not a UI button

    companion object: Mapper<ButtonClickComponent>()
}