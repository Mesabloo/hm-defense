package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.JsonReader
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.MachinePart
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.PPM
import ktx.box2d.body
import ktx.box2d.box
import ktx.math.vec2

class MachineModel(name: String, level: Int) {
    // Main body
    private val bodySize: Pair<Float, Float>

    // Left weapon
    private val leftWeaponSize: Pair<Float, Float>
    private val leftWeaponOffset: Pair<Float, Float>

    // Right weapon
    private val rightWeaponSize: Pair<Float, Float>
    private val rightWeaponOffset: Pair<Float, Float>

    // TODO: feet

    init {
        val handle = Gdx.files.internal("data/models/machines/${name}-${level.toString().padStart(2, '0')}.json")
        val data = JsonReader().parse(handle.readString())

        assert(data.isObject)

        val body = data.get("body")
        val weapons = data.get("weapons")
        val feet = data.get("feet")

        ////////

        val bodySize = body.get("size").asFloatArray()
        this.bodySize = Pair(bodySize[0], bodySize[1])

        val leftWeapon = weapons.get("left")
        val leftWeaponSize = leftWeapon.get("size").asFloatArray()
        val leftWeaponOffset = leftWeapon.get("offset").asFloatArray()
        this.leftWeaponSize = Pair(leftWeaponSize[0], leftWeaponSize[1])
        this.leftWeaponOffset = Pair(leftWeaponOffset[0], leftWeaponOffset[1])

        val rightWeapon = weapons.get("right")
        val rightWeaponSize = rightWeapon.get("size").asFloatArray()
        val rightWeaponOffset = rightWeapon.get("offset").asFloatArray()
        this.rightWeaponSize = Pair(rightWeaponSize[0], rightWeaponSize[1])
        this.rightWeaponOffset = Pair(rightWeaponOffset[0], rightWeaponOffset[1])

        // TODO: feet
    }

    fun toPositionedBody(world: World, pos: Vector2): Body =
        world.body {
            type = BodyDef.BodyType.KinematicBody
            // main body
            box(
                width = this@MachineModel.bodySize.first / PPM,
                height = this@MachineModel.bodySize.second / PPM
            ) {
                density = 10f
                restitution = 0f
                friction = 1f
                userData = MachinePart.BODY
                isSensor = false
            }
            // left weapon
            box(
                width = this@MachineModel.leftWeaponSize.first / PPM,
                height = this@MachineModel.leftWeaponSize.second / PPM,
                position = vec2(
                    this@MachineModel.leftWeaponOffset.first / PPM,
                    this@MachineModel.leftWeaponOffset.second / PPM
                )
            ) {
                density = 0f
                restitution = 0f
                isSensor = true
                friction = 0f
                userData = MachinePart.LEFT_WEAPON
            }
            // right weapon
            box(
                width = this@MachineModel.rightWeaponSize.first / PPM,
                height = this@MachineModel.rightWeaponSize.second / PPM,
                position = vec2(
                    this@MachineModel.rightWeaponOffset.first / PPM,
                    this@MachineModel.rightWeaponOffset.second / PPM
                )
            ) {
                density = 0f
                restitution = 0f
                isSensor = true
                friction = 0f
                userData = MachinePart.RIGHT_WEAPON
            }
            position.set(
                (pos.x + this@MachineModel.bodySize.first / 2f) / PPM,
                (pos.y + this@MachineModel.bodySize.second / 2f) / PPM
            )
        }
}