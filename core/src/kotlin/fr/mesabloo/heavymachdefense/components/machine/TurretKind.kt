package fr.mesabloo.heavymachdefense.components.machine

import java.io.Serializable

enum class TurretKind(val turretName: String) : Serializable {
    RIFLE("rifle"),
    MISSILE("missile"),
    VULCAN("vulcan"),
    PLASMA("plasma"),
    ION("ion"),
    LASER("laser");
}