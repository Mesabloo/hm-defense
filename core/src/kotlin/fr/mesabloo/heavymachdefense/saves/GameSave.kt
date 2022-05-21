package fr.mesabloo.heavymachdefense.saves

import fr.mesabloo.heavymachdefense.components.machine.MachineKind
import fr.mesabloo.heavymachdefense.components.machine.TurretKind
import java.io.Serializable
import java.util.*

class GameSave : Serializable {
    companion object {
        @Transient
        const val PREFERENCES_PATH = "gamesaves"
    }

    var creationDate: Date = Date()
    var lastAccessedDate: Date = Date()
    var lastStageCompleted: Int = 0
    var credits: Int = 0
    var name: String = "<<placeholder>>"
    // TODO: upgrades (machine, turrets and general upgrades)
    var machineUpgrades: HashMap<MachineKind, Int> = hashMapOf(
        Pair(MachineKind.RIFLE, 1),
        Pair(MachineKind.MISSILE, 1),
        Pair(MachineKind.HMG, 1),
        Pair(MachineKind.SHOTGUN, 1),
        Pair(MachineKind.PLASMA, 1),
        Pair(MachineKind.ION, 1),
        Pair(MachineKind.HEAVY_MISSILE, 1)
    )
    var turretUpgrades: HashMap<TurretKind, Int> = hashMapOf(
        Pair(TurretKind.RIFLE, 1),
        Pair(TurretKind.MISSILE, 1),
        Pair(TurretKind.VULCAN, 1),
        Pair(TurretKind.PLASMA, 1),
        Pair(TurretKind.ION, 1),
        Pair(TurretKind.LASER, 1)
    )
    var mainUpgrades: HashMap<String, Int> = hashMapOf()
}