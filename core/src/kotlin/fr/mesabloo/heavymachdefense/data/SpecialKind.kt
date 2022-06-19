package fr.mesabloo.heavymachdefense.data

enum class SpecialKind(val iconTilePrefix: String) {
    AIRSTRIKE_BOMB("0200"),
    AIRSTRIKE_MISSILE("0201"),
    AIRSTRIKE_NUKE("0202"),
    AIRSTRIKE_EMP("0203"),
    // TODO: 0204 -> laser
    CROSSFIRE_MISSILE("0210")
    // TODO: 0211 -> flamethrower
}