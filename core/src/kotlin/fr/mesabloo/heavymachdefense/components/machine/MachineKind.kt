package fr.mesabloo.heavymachdefense.components.machine

enum class MachineKind {
    RIFLE,
    MISSILE,
    HEAVY_MISSILE,
    ION,
    HMG,
    PLASMA,
    SHOTGUN;

    val machineName: String by lazy {
        when (this) {
            RIFLE -> "rifle"
            MISSILE -> "missile"
            HEAVY_MISSILE -> "heavy-missile"
            ION -> "ion"
            HMG -> "hmg"
            PLASMA -> "plasma"
            SHOTGUN -> "shotgun"
        }
    }
}