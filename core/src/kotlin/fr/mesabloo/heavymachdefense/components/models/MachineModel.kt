package fr.mesabloo.heavymachdefense.components.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.JsonReader
import fr.mesabloo.heavymachdefense.MachinePart
import fr.mesabloo.heavymachdefense.PPM
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.distanceJointWith
import ktx.math.vec2

/**
 * A class to hold very low-level construction-oriented sizes, offsets etc. to correctly create machines dynamically.
 * The specification format is a JSON file of this type:
 *
 * ```
 * {
 *   "body": {
 *     "size": float[2]
 *   },
 *   "weapons": {
 *     "left": {
 *       "size": float[2],
 *       "offset": float[2]
 *     },
 *     "right": {
 *       "size": float[2],
 *       "offset": float[2]
 *     }
 *   },
 *   "feet": {
 *     // TODO
 *   }
 * }
 * ```
 *
 * Where:
 * - the `size` fields are pairs of floats representing the size on the X and Y axes.
 * - the `offset` fields are pairs of floats representing the distance from the center of the body on the X and Y axes.
 *
 * This JSON file must be named `${name}-${level%02d}.json` and must be located in the
 * `data/models/machines` internal folder.
 *
 * @param name The machine name, obtained from the
 * [fr.mesabloo.heavymachdefense.components.machine.MachineKind.machineName] property
 * @param level Which level (between the lowest and the highest, most likely to be 0 to 10) to construct the machine for
 */
class MachineModel(name: String, level: Int) {
    /**
     * The position of the main body on the X and Y axes, in pixels.
     */
    private val bodySize: Pair<Float, Float>

    /**
     * The size of the left weapon on the X and Y axes, in pixels.
     */
    private val leftWeaponSize: Pair<Float, Float>

    /**
     * The offset from the center of the main body, in pixels.
     */
    private val leftWeaponOffset: Pair<Float, Float>

    /**
     * The size of the left weapon on the X and Y axes, in pixels.
     */
    private val rightWeaponSize: Pair<Float, Float>

    /**
     * The offset from the center of the main body, in pixels.
     */
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

    /**
     * Creates some jointed bodies from the model specification parsed in the constructor.
     *
     * @param world The [World] in which the bodies will live in
     * @param pos The spawning position of the machine, corresponding to the center of the body, in world units
     *
     * @return A pair containing all the bodies created as well as the joints between them
     */
    fun toPositionedBody(world: World, pos: Vector2): Pair<List<Body>, List<Joint>> {
        val center = vec2(
            (pos.x + this@MachineModel.bodySize.first / 2f) / PPM,
            (pos.y + this@MachineModel.bodySize.second / 2f) / PPM
        )

        val bodies = listOf(
            world.body {
                type = BodyDef.BodyType.KinematicBody
                box(
                    width = this@MachineModel.bodySize.first / PPM,
                    height = this@MachineModel.bodySize.second / PPM
                ) {
                    density = 10f
                    restitution = 0f
                    friction = 1f
                    isSensor = false
                }
                userData = MachinePart.BODY
                position.set(center)
            },
            world.body {
                type = BodyDef.BodyType.KinematicBody
                box(
                    width = this@MachineModel.leftWeaponSize.first / PPM,
                    height = this@MachineModel.leftWeaponSize.second / PPM
                ) {
                    density = 0f
                    restitution = 0f
                    isSensor = true
                    friction = 0f
                }
                userData = MachinePart.LEFT_WEAPON
                position.set(
                    center.x + this@MachineModel.leftWeaponOffset.first / PPM,
                    center.y + this@MachineModel.leftWeaponOffset.second / PPM
                )
            },
            world.body {
                type = BodyDef.BodyType.KinematicBody
                box(
                    width = this@MachineModel.rightWeaponSize.first / PPM,
                    height = this@MachineModel.rightWeaponSize.second / PPM,
                ) {
                    density = 0f
                    restitution = 0f
                    isSensor = true
                    friction = 0f
                }
                userData = MachinePart.RIGHT_WEAPON
                position.set(
                    center.x + this@MachineModel.rightWeaponOffset.first / PPM,
                    center.y + this@MachineModel.rightWeaponOffset.second / PPM
                )
            }
        )
        val joints = listOf(
            bodies[0].distanceJointWith(bodies[1]) {
                frequencyHz = 0f
                dampingRatio = 1f
            },
            bodies[0].distanceJointWith(bodies[2]) {
                frequencyHz = 0f
                dampingRatio = 1f
            }
        )

        return Pair(bodies, joints)
    }
}