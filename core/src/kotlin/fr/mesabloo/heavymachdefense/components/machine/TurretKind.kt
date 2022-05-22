package fr.mesabloo.heavymachdefense.components.machine

import java.io.Serializable

/**
 * An enumeration containing all the available turret kinds, mapped to their low-level names.
 *
 * @property turretName The low-level of each turret kind.
 */
enum class TurretKind(val turretName: String) : Serializable {
    RIFLE("rifle"),
    MISSILE("missile"),
    VULCAN("vulcan"),
    PLASMA("plasma"),
    ION("ion"),
    LASER("laser");
}