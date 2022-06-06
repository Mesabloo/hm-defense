package fr.mesabloo.heavymachdefense.entities

import com.badlogic.gdx.physics.box2d.BodyDef
import fr.mesabloo.heavymachdefense.BG_BORDER
import fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.world.GameWorld
import ktx.box2d.body
import ktx.box2d.box
import ktx.math.vec2

fun createTerrainBody(world: GameWorld) {
    val width = 512f / PPM
    val height = 2048f / PPM

    world.world.body {
        type = BodyDef.BodyType.StaticBody
        box(width, height, position = vec2(width / 2f, height / 2f)) {
            isSensor = false
            userData = BG_BORDER
        }

        //position.set(-512f / PPM, -2048f / PPM)
    }
}