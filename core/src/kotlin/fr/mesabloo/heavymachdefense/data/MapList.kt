package fr.mesabloo.heavymachdefense.data

import com.badlogic.gdx.Gdx
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
private data class MapInfo(val level: Int, val background: Int)

private lateinit var maps: MutableList<Int>

fun getBackgroundForLevel(level: Int): Int {
    assert(level in 1..80)

    if (!::maps.isInitialized) {
        maps = mutableListOf()

        val list: List<MapInfo> = Json.decodeFromString(Gdx.files.internal("data/map-list.json").readString())
        for (map in list) {
            maps.add(map.level - 1, map.background)
        }
    }

    return maps[level - 1]
}