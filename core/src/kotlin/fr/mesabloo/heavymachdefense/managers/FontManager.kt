package fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable

class FontManager : Disposable {
    companion object {
        const val CREDITS: String = "credits"
        const val LEVEL: String = "level"
        const val MINERAL: String = "mineral"
        const val STAGE: String = "stage"
        const val SET01B: String = "set_01b"

        const val TREBUCHET_MS_12: String = "trebuchet_ms"
        const val TREBUCHET_MS_BOLD_24: String = "trebuchet_ms_bd_24"
        const val TREBUCHET_MS_BOLD_12: String = "trebuchet_ms_bd"
    }

    fun init() {
        listOf(CREDITS, LEVEL, MINERAL, STAGE, SET01B, TREBUCHET_MS_12, TREBUCHET_MS_BOLD_24, TREBUCHET_MS_BOLD_12)
            .forEach { fontName ->
                this.bitmapFonts[fontName] = BitmapFont(Gdx.files.internal("fonts/${fontName}.fnt"))
            }
        this.bitmapFonts[TREBUCHET_MS_BOLD_24]!!.color = Color.CYAN
    }

    override fun dispose() {
        for ((_, value) in this.bitmapFonts) {
            value.dispose()
        }
        this.bitmapFonts.clear()
    }

    /////////////////// INTERNAL ////////////////////

    val bitmapFonts: MutableMap<String, BitmapFont> = mutableMapOf()
}

val fontManager by lazy { FontManager() }