package io.github.mesabloo.hmdefense.data.models

import spack.{Pack, Unpack}

// Derive Pack and Unpack for 2-uples.
given [T: Pack, U: Pack]: Pack[(T, U)] = Pack.derived
given [T: Unpack, U: Unpack]: Unpack[(T, U)] = Unpack.derived

/** This is an intermediate representation for serialized data related to
  * machine models.
  *
  * @param level
  *   The level of the machine this model represents.
  * @param machine
  *   The name of the machine, as found in the texture atlases
  *   `gfx/models/machines/bodies.atlas` and
  *   `gfx/models/machines/weapons.atlas`.
  * @param lwoff
  *   The relative offset, from the center of the left weapon sprite to the
  *   center of the body sprite.
  * @param rwoff
  *   The relative offset, from the center of the right weapon sprite to the
  *   center of the body sprite.
  * @param feet
  *   The identifier associated to the feet in use for this machine.
  * @param lfoff
  *   The relative offset, from the center of the left feet sprite to the center
  *   of the body sprite.
  * @param rfoff
  *   The relative offset, from the center of the left feet sprite to the center
  *   of the body sprite.
  */
case class MachineModel(
    level: Int,
    machine: String,
    lwoff: (Int, Int),
    rwoff: (Int, Int),
    feet: Int,
    lfoff: (Int, Int),
    rfoff: (Int, Int)
) derives Pack,
      Unpack
