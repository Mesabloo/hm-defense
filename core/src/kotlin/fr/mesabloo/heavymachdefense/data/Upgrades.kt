package fr.mesabloo.heavymachdefense.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Upgrades(
    @SerialName("BASE_CANNON") val base_cannon: List<BaseCannonUpgrade>,
    @SerialName("BASE_DEFENSE") val base_defense: List<BaseDefenseUpgrade>,
    @SerialName("BUILD_TIME") val build_time: List<BuildTimeUpgrade>,
    @SerialName("CR_RESEARCH") val cr_research: List<CrResearchUpgrade>,
    @SerialName("CELL_RESEARCH") val cell_research: List<CellResearchUpgrade>,
    @SerialName("CELL_STORAGE") val cell_storage: List<CellStorageUpgrade>
)

@Serializable
data class BaseCannonUpgrade(val cost: Long, val attack: Long)

@Serializable
data class BaseDefenseUpgrade(val cost: Long, val defense: Long)

@Serializable
data class BuildTimeUpgrade(val cost: Long, val multiplier: Float)

@Serializable
data class CrResearchUpgrade(val cost: Long, val multiplier: Float)

@Serializable
data class CellResearchUpgrade(val cost: Long, val multiplier: Float)

@Serializable
data class CellStorageUpgrade(val cost: Long, val storage: Long)