package fr.mesabloo.heavymachdefense.data

import java.io.Serializable

/**
 * An enumeration containing all the available machine kinds, mapped to their low-level names.
 *
 * @property machineName The low-level of each machine kind.
 */
enum class MachineKind(val machineName: String) : Serializable {
    RIFLE("rifle"),
    MISSILE("missile"),
    HEAVY_MISSILE("heavy-missile"),
    ION("ion"),
    HMG("hmg"),
    PLASMA("plasma"),
    SHOTGUN("shotgun");
}