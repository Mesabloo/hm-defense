package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class ButtonClickComponent : Component {
    var alreadyClicked: Boolean = false
    lateinit var buttonKind: Button

    companion object: Mapper<ButtonClickComponent>()
}