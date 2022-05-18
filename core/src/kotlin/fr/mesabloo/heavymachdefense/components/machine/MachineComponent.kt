package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import ktx.ashley.Mapper

class MachineComponent(var level: Long = 0L) : Component {
    lateinit var kind: MachineKind
    lateinit var body: Body

    companion object: Mapper<MachineComponent>()
}