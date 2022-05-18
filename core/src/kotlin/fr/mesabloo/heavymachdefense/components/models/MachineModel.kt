package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.models

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import kotlin.properties.Delegates

class MachineModel(private val level: Int) : Json.Serializable {
    private lateinit var bodyTile: String
    private var bodyWidth by Delegates.notNull<Int>()
    private var bodyHeight by Delegates.notNull<Int>()

    override fun write(json: Json) {
        TODO("Not yet implemented")
    }

    override fun read(json: Json, data: JsonValue) {

    }
}