package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense

// 16 pixels = 1 meter
const val PPM: Float = 16f

enum class MachinePart {
    LEFT_FOOT,
    RIGHT_FOOT,
    BODY,
    LEFT_WEAPON,
    RIGHT_WEAPON;
}

const val BG_BORDER = 0x1