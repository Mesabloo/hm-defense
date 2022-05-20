package fr.mesabloo.heavymachdefense.components.machine

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import ktx.ashley.Mapper

class MachineComponent(var level: Long = 0L) : Component {
    lateinit var kind: MachineKind
    lateinit var mainBody: Body
    lateinit var lWeaponBody: Body
    lateinit var rWeaponBody: Body
    // TODO: feet
    lateinit var joints: List<Joint>

    companion object: Mapper<MachineComponent>()
}