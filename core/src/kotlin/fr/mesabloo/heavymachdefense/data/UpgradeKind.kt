package fr.mesabloo.heavymachdefense.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UpgradeKind(@Transient val str: String) {
    UPGRADE("upgrade"),
    CLOSE("close"),
    @SerialName("BASE_CANNON") BASE_CANNON("base-cannon"),
    BASE_DEFENSE("base-defense"),
    BUILD_TIME("build-time"),
    CELL_STORAGE("cell-storage"),
    CELL_RESEARCH("cell-research"),
    CR_RESEARCH("cr-research")
}