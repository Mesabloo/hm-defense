package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense

// 16 pixels = 1 meter
const val PPM: Float = 16f

enum class MachinePart {
    BODY,
    LEFT_WEAPON,
    RIGHT_WEAPON,
    LEFT_FOOT,
    RIGHT_FOOT;
}

const val BG_BORDER = 0x1