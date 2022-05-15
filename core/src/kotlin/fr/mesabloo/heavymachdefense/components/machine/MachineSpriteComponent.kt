package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class MachineSpriteComponent(var level: Long = 0L) : Component {
    lateinit var kind: MachineKind

    companion object: Mapper<MachineSpriteComponent>()
}