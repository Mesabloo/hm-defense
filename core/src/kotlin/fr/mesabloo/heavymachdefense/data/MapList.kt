package fr.mesabloo.heavymachdefense.data

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.JsonReader

private lateinit var maps: MutableList<Int>

fun getBackgroundForLevel(level: Int): Int {
    assert(level in 1..80)

    if (!::maps.isInitialized) {
        maps = mutableListOf()

        val value = JsonReader().parse(Gdx.files.internal("data/map-list.json"))
        @Suppress("NAME_SHADOWING")
        for (value in value) {
            val level = value.getInt("level")
            val background = value.getInt("background")

            maps.add(level - 1, background)
        }
    }

    return maps[level - 1]
}