package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class StageButtonComponent : Component {
    var level: Int = 0

    companion object: Mapper<StageButtonComponent>()
}