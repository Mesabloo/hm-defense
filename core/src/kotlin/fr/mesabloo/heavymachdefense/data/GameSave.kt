package fr.mesabloo.heavymachdefense.data

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
        Pair(MachineKind.RIFLE, 1),
        Pair(MachineKind.MISSILE, 1),
        Pair(MachineKind.HMG, 1),
        Pair(MachineKind.SHOTGUN, 1),
        Pair(MachineKind.PLASMA, 1),
        Pair(MachineKind.ION, 1),
        Pair(MachineKind.HEAVY_MISSILE, 1)
    ),
    var turretUpgrades: HashMap<TurretKind, Int> = hashMapOf(
        Pair(TurretKind.RIFLE, 1),
        Pair(TurretKind.MISSILE, 1),
        Pair(TurretKind.VULCAN, 1),
        Pair(TurretKind.PLASMA, 1),
        Pair(TurretKind.ION, 1),
        Pair(TurretKind.LASER, 1)
    ),
    var mainUpgrades: HashMap<UpgradeKind, Int> = hashMapOf(
        Pair(UpgradeKind.BASE_CANNON, 1),
        Pair(UpgradeKind.BASE_DEFENSE, 1),
        Pair(UpgradeKind.BUILD_TIME, 1),
        Pair(UpgradeKind.CR_RESEARCH, 1),
        Pair(UpgradeKind.CELL_RESEARCH, 1),
        Pair(UpgradeKind.CELL_STORAGE, 1)
    ),
    var buildSlots: MutableList<Slot> = mutableListOf(
        MachineSlot(MachineKind.RIFLE)
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