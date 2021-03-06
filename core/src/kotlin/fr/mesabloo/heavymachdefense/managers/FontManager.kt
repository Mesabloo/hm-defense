package fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable

class FontManager : Disposable {
    companion object {
        const val CREDITS: String = "credits"
        const val LEVEL: String = "level"
        const val MINERAL: String = "mineral"
        const val STAGE: String = "stage"
        const val SET01B: String = "set_01b"

        const val TREBUCHET_MS_BOLD_11_WHITE: String = "trebuchet_ms_bd_11_white"

        const val TREBUCHET_MS_BOLD_12_WHITE: String = "trebuchet_ms_bd_12_white"

        const val TREBUCHET_MS_BOLD_16_WHITE: String = "trebuchet_ms_bd_16_white"

        const val TREBUCHET_MS_BOLD_18: String = "trebuchet_ms_bd_18"

        const val TREBUCHET_MS_20_BLUE: String = "trebuchet_ms_20_blue"

        const val TREBUCHET_MS_26_BLUE: String = "trebuchet_ms_26_blue"

        const val TREBUCHET_MS_BOLD_28_BLUE: String = "trebuchet_ms_bd_28_blue"
        const val TREBUCHET_MS_BOLD_28_BLACK: String = "trebuchet_ms_bd_28_black"
    }

    fun init() {
        listOf(
            CREDITS,
            LEVEL,
            MINERAL,
            STAGE,
            SET01B,
            TREBUCHET_MS_BOLD_28_BLUE,
            TREBUCHET_MS_BOLD_28_BLACK,
            TREBUCHET_MS_20_BLUE,
            TREBUCHET_MS_26_BLUE,
            TREBUCHET_MS_BOLD_11_WHITE,
            TREBUCHET_MS_BOLD_16_WHITE,
            TREBUCHET_MS_BOLD_12_WHITE,
            TREBUCHET_MS_BOLD_18
        )
            .forEach { fontName ->
                this.bitmapFonts[fontName] = BitmapFont(Gdx.files.internal("fonts/${fontName}.fnt"))
                this.bitmapFonts[fontName]!!.setUseIntegerPositions(false)
            }
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