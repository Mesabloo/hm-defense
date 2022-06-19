package fr.mesabloo.heavymachdefense.data

import kotlinx.serialization.Serializable

@Serializable
abstract class SpecialInfo

@Serializable
data class AirstrikeBombInfo(
    val unitDamage: Int,
    val explosionSize: Int,
    val bombCount: Int,
    val range: Int,
) : SpecialInfo()

@Serializable
data class AirstrikeMissileInfo(
    val unitDamage: Int,
    val explosionSize: Int,
    val bombCount: Int,
    val range: Int
) : SpecialInfo()

@Serializable
data class AirstrikeNukeInfo(
    val unitDamage: Int,
    val explosionSize: Int,
    val range: Int
) : SpecialInfo()

@Serializable
data class AirStrikeEMPInfo(
    val unitDamage: Int,
    val explosionSize: Int,
    val range: Int,
    val paralysisTime: Int
) : SpecialInfo()

@Serializable
data class CrossfireMissileInfo(
    val unitDamage: Int,
    val explosionSize: Int,
    val range: Int,
    val missileCount: Int
) : SpecialInfo()

@Serializable
data class Specials(
    val airstrikeBomb: List<AirstrikeBombInfo>,
    val airstrikeMissile: List<AirstrikeMissileInfo>,
    val airstrikeNuke: List<AirstrikeNukeInfo>,
    val airstrikeEMP: List<AirStrikeEMPInfo>,
    val crossfireMissile: List<CrossfireMissileInfo>,
)