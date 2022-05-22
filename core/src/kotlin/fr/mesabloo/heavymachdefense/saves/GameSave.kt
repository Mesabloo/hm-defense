package fr.mesabloo.heavymachdefense.saves

import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import fr.mesabloo.heavymachdefense.components.machine.MachineKind
import fr.mesabloo.heavymachdefense.components.machine.TurretKind
import ktx.json.readValue
import java.util.*

class GameSave {
    companion object {
        @Transient
        const val PREFERENCES_PATH = "gamesaves"
    }

    var creationDate: Date = Date()
    var lastAccessedDate: Date = Date()
    var lastStageCompleted: Int = 0
    var credits: Long = 0L
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

object GameSaveJsonSerializer : Json.Serializer<GameSave> {
    override fun read(json: Json, data: JsonValue, type: Class<*>): GameSave? {
        val save = GameSave()

        try {
            save.creationDate = json.readValue<Long?>(data, "creationDate")?.let { Date(it) } ?: Date()
            save.lastAccessedDate = json.readValue<Long?>(data, "lastAccessedDate")?.let { Date(it) } ?: Date()
            save.lastStageCompleted = json.readValue(data, "lastStageCompleted") ?: 0
            save.credits = json.readValue(data, "credits") ?: 0
            save.name = json.readValue(data, "name") ?: ""
            json.readValue<HashMap<MachineKind, Int>?>(data, "machineUpgrades")?.let { save.machineUpgrades = it }
            json.readValue<HashMap<TurretKind, Int>?>(data, "turretUpgrades")?.let { save.turretUpgrades = it }
            json.readValue<HashMap<String, Int>?>(data, "mainUpgrades")?.let { save.mainUpgrades = it }
        } catch (ex: GdxRuntimeException) {
            return null
        }

        return save
    }

    override fun write(json: Json, `object`: GameSave, knownType: Class<*>) {
        TODO("Not yet implemented")
    }
}