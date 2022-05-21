package fr.mesabloo.heavymachdefense.components.machine

import java.io.Serializable

enum class MachineKind(val machineName: String) : Serializable {
    RIFLE("rifle"),
    MISSILE("missile"),
    HEAVY_MISSILE("heavy-missile"),
    ION("ion"),
    HMG("hmg"),
    PLASMA("plasma"),
    SHOTGUN("shotgun");
}