package fr.mesabloo.heavymachdefense.data

import fr.mesabloo.heavymachdefense.data.SpecialKind
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@Serializable
data class GameSave(
    @Serializable(with = DateTimestampSerializer::class)
    var creationDate: Date,
    @Serializable(with = DateTimestampSerializer::class)
    var lastAccessedDate: Date,
    var lastStageCompleted: Int = 0,
    var credits: Long = 0L,
    var name: String = "<<placeholder>>",
    var machineUpgrades: HashMap<MachineKind, Int> = hashMapOf(
        MachineKind.RIFLE to 1,
        MachineKind.MISSILE to 1,
        MachineKind.HMG to 1,
        MachineKind.SHOTGUN to 1,
        MachineKind.PLASMA to 1,
        MachineKind.ION to 1,
        MachineKind.HEAVY_MISSILE to 1,
        MachineKind.TANKER to 1
    ),
    var turretUpgrades: HashMap<TurretKind, Int> = hashMapOf(
        TurretKind.RIFLE to 1,
        TurretKind.MISSILE to 1,
        TurretKind.VULCAN to 1,
        TurretKind.PLASMA to 1,
        TurretKind.ION to 1,
        TurretKind.LASER to 1
    ),
    var mainUpgrades: HashMap<UpgradeKind, Int> = hashMapOf(
        UpgradeKind.BASE_CANNON to 1,
        UpgradeKind.BASE_DEFENSE to 1,
        UpgradeKind.BUILD_TIME to 1,
        UpgradeKind.CR_RESEARCH to 1,
        UpgradeKind.CELL_RESEARCH to 1,
        UpgradeKind.CELL_STORAGE to 1
    ),
    var specialUpgrades: HashMap<SpecialKind, Int> = hashMapOf(
        SpecialKind.AIRSTRIKE_BOMB to 1,
        SpecialKind.AIRSTRIKE_MISSILE to 1,
        SpecialKind.AIRSTRIKE_NUKE to 1,
        SpecialKind.AIRSTRIKE_EMP to 1,
        SpecialKind.CROSSFIRE_MISSILE to 1
    ),
    var buildSlots: MutableList<Slot> = mutableListOf(
        MachineSlot(MachineKind.RIFLE)
    ),
    var specialSlots: MutableList<Slot> = mutableListOf(
        SpecialSlot(SpecialKind.AIRSTRIKE_BOMB),
        SpecialSlot(SpecialKind.AIRSTRIKE_MISSILE),
        SpecialSlot(SpecialKind.AIRSTRIKE_NUKE),
        SpecialSlot(SpecialKind.AIRSTRIKE_EMP),
        SpecialSlot(SpecialKind.CROSSFIRE_MISSILE)
    ),
    var specialCount: HashMap<SpecialKind, Int> = hashMapOf(
        SpecialKind.AIRSTRIKE_BOMB to 0,
        SpecialKind.AIRSTRIKE_MISSILE to 0,
        SpecialKind.AIRSTRIKE_NUKE to 0,
        SpecialKind.AIRSTRIKE_EMP to 0,
        SpecialKind.CROSSFIRE_MISSILE to 0
    )
) {
    companion object {
        @Transient
        const val PREFERENCES_PATH = "hm-defense/saves"
    }
}

object DateTimestampSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): Date = Date(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
}