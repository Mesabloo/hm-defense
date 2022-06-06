package fr.mesabloo.heavymachdefense.data.models

import kotlinx.serialization.Serializable

@Serializable
data class WeaponModel(val originX: Float, val originY: Float)

@Serializable
data class PositionModel(val x: Float, val y: Float)

@Serializable
data class RadarModel(val originX: Float, val originY: Float)

@Serializable
data class BaseModel(val weapons: List<PositionModel>, val radar: PositionModel)