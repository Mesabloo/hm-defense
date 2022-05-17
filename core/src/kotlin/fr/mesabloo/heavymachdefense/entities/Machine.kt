package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.World
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.BodyComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineKind
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineSpriteComponent
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box
import ktx.math.vec2

fun createMachine(engine: Engine, world: World, kind: MachineKind, level: Long): Entity = engine.entity {
    val width = 32f
    val height = 32f

    with<BodyComponent> {
        this.world = world
        this.body = world.body {
            box(width = width, height = height, position = vec2(50f, 50f)) {
                density = 100000f
                restitution = 0f
            }
        }
        this.width = width
        this.height = height
    }
    with<MachineComponent> {
        this.kind = kind
        this.level = level
    }
//    with<MachineMovementComponent> {
//
//    }
    with<MachineSpriteComponent> {
        this.kind = kind
        this.level = level
    }
//    with<MachineIAComponent> {
//        this.kind = kind
//    }
}