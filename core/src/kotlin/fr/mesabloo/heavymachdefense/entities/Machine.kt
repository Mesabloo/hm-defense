package fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.World
import fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.data.MachineKind
import fr.mesabloo.heavymachdefense.data.models.MachineModel
import ktx.ashley.entity
import ktx.ashley.with
import ktx.math.vec2

fun createMachine(engine: Engine, world: World, kind: MachineKind, level: Int): Entity = engine.entity {
    val width = 32f
    val height = 32f

    val x = 64f
    val y = 64f

//    val mainBody = world.body {
//        type = BodyDef.BodyType.KinematicBody
//        // main body
//        box(width = 32f / PPM, height = 32f / PPM) {
//            density = 10f
//            restitution = 0f
//            friction = 1f
//            userData = MachinePart.BODY
//            isSensor = false
//        }
//        // left weapon
//        box(width = 32f / PPM, height = 16f / PPM, position = vec2(8f / PPM, 14f / PPM)) {
//            density = 0f
//            restitution = 0f
//            isSensor = true
//            friction = 0f
//            userData = MachinePart.LEFT_WEAPON
//        }
//        // right weapon
//        box(width = 32f / PPM, height = 16f / PPM, position = vec2(8f / PPM, -16f / PPM)) {
//            density = 0f
//            restitution = 0f
//            isSensor = true
//            friction = 0f
//            userData = MachinePart.RIGHT_WEAPON
//        }
//        position.set((x + 32f / 2f) / PPM, (y + 32f / 2f) / PPM)
//    }
    val (bodies, joints) = MachineModel("rifle", level).toPositionedBody(world, vec2(x, y))

    with<MachineComponent> {
        this.kind = kind
        this.level = level

        this.mainBody = bodies[0]
        this.lWeaponBody = bodies[1]
        this.rWeaponBody = bodies[2]

        this.joints = joints
    }
//    with<MachineMovementComponent> {
//
//    }
}