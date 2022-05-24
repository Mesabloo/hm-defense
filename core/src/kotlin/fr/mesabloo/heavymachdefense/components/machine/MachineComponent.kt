package fr.mesabloo.heavymachdefense.components.machine

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import fr.mesabloo.heavymachdefense.data.MachineKind
import ktx.ashley.Mapper

// TODO: might have to change
class MachineComponent(var level: Int = 0) : Component {
    lateinit var kind: MachineKind
    lateinit var mainBody: Body
    lateinit var lWeaponBody: Body
    lateinit var rWeaponBody: Body
    // TODO: feet
    lateinit var joints: List<Joint>

    companion object: Mapper<MachineComponent>()
}