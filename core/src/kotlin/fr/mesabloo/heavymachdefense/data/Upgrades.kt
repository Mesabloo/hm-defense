package fr.mesabloo.heavymachdefense.data

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import fr.mesabloo.heavymachdefense.data.UpgradeKind
import ktx.json.readArrayValue
import ktx.json.readValue

data class Upgrades(
    val base_cannon: List<BaseCannonUpgrade>,
    val base_defense: List<BaseDefenseUpgrade>,
    val build_time: List<BuildTimeUpgrade>,
    val cr_research: List<CrResearchUpgrade>,
    val cell_research: List<CellResearchUpgrade>,
    val cell_storage: List<CellStorageUpgrade>
)

data class BaseCannonUpgrade(val cost: Long, val attack: Long)

data class BaseDefenseUpgrade(val cost: Long, val defense: Long)

data class BuildTimeUpgrade(val cost: Long, val multiplier: Float)

data class CrResearchUpgrade(val cost: Long, val multiplier: Float)

data class CellResearchUpgrade(val cost: Long, val multiplier: Float)

data class CellStorageUpgrade(val cost: Long, val storage: Long)

///////////////////////////////////////////////////////////

object BaseCannonUpgradeSerializer : Json.ReadOnlySerializer<BaseCannonUpgrade>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): BaseCannonUpgrade =
        BaseCannonUpgrade(
            json.readValue(jsonData, "cost"),
            json.readValue(jsonData, "attack")
        )
}

object BaseDefenseUpgradeSerializer : Json.ReadOnlySerializer<BaseDefenseUpgrade>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): BaseDefenseUpgrade =
        BaseDefenseUpgrade(
            json.readValue(jsonData, "cost"),
            json.readValue(jsonData, "defense")
        )
}

object BuildTimeUpgradeSerializer : Json.ReadOnlySerializer<BuildTimeUpgrade>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): BuildTimeUpgrade =
        BuildTimeUpgrade(
            json.readValue(jsonData, "cost"),
            json.readValue(jsonData, "multiplier")
        )
}

object CrResearchUpgradeSerializer : Json.ReadOnlySerializer<CrResearchUpgrade>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): CrResearchUpgrade =
        CrResearchUpgrade(
            json.readValue(jsonData, "cost"),
            json.readValue(jsonData, "multiplier")
        )
}

object CellResearchUpgradeSerializer : Json.ReadOnlySerializer<CellResearchUpgrade>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): CellResearchUpgrade =
        CellResearchUpgrade(
            json.readValue(jsonData, "cost"),
            json.readValue(jsonData, "multiplier")
        )
}

object CellStorageUpgradeSerializer : Json.ReadOnlySerializer<CellStorageUpgrade>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): CellStorageUpgrade =
        CellStorageUpgrade(
            json.readValue(jsonData, "cost"),
            json.readValue(jsonData, "storage")
        )
}

object UpgradesJsonSerializer : Json.ReadOnlySerializer<Upgrades>() {
    override fun read(json: Json, jsonData: JsonValue, type: Class<*>): Upgrades =
        Upgrades(
            json.readArrayValue(jsonData, UpgradeKind.BASE_CANNON.name),
            json.readArrayValue(jsonData, UpgradeKind.BASE_DEFENSE.name),
            json.readArrayValue(jsonData, UpgradeKind.BUILD_TIME.name),
            json.readArrayValue(jsonData, UpgradeKind.CR_RESEARCH.name),
            json.readArrayValue(jsonData, UpgradeKind.CELL_RESEARCH.name),
            json.readArrayValue(jsonData, UpgradeKind.CELL_STORAGE.name)
        )
}