package fr.mesabloo.heavymachdefense.data

import fr.mesabloo.heavymachdefense.data.MachineKind
import fr.mesabloo.heavymachdefense.data.TurretKind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuildInfo(val cellCost: Int, val time: Float, @SerialName("max") val maxAllowed: Int)

@Serializable
data class Builds(
    val machines: HashMap<Pair<MachineKind, Int>, BuildInfo>,
    val turrets: HashMap<Pair<TurretKind, Int>, BuildInfo>
)