package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.MachinePart
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineKind
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box
import ktx.math.vec2

fun createMachine(engine: Engine, world: World, kind: MachineKind, level: Long): Entity = engine.entity {
    val width = 32f
    val height = 32f

    val mainBody = world.body {
        type = BodyDef.BodyType.KinematicBody
        // main body
        box(width = 32f / PPM, height = 32f / PPM) {
            density = 10f
            restitution = 0f
            friction = 1f
            userData = MachinePart.BODY
            isSensor = false
        }
        // left weapon
        box(width = 32f / PPM, height = 16f / PPM, position = vec2(10f / PPM, 20f / PPM)) {
            density = 0f
            restitution = 0f
            isSensor = true
            friction = 0f
            userData = MachinePart.LEFT_WEAPON
        }
        // right weapon
        box(width = 32f / PPM, height = 16f / PPM, position = vec2(10f / PPM, -20f / PPM)) {
            density = 0f
            restitution = 0f
            isSensor = true
            friction = 0f
            userData = MachinePart.RIGHT_WEAPON
        }
        position.set((16f + 32f / 2f) / PPM, (16f + 32f / 2f) / PPM)
    }

    with<MachineComponent> {
        this.kind = kind
        this.level = level

        this.body = mainBody
    }
//    with<MachineMovementComponent> {
//
//    }
}