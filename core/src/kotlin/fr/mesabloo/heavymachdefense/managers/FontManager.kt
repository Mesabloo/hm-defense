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

        const val TREBUCHET_MS: String = "trebuchet_ms"
        const val TREBUCHET_MS_BOLD: String = "trebuchet_ms_bd"
    }

    fun init() {
        listOf(CREDITS, LEVEL, MINERAL, STAGE, TREBUCHET_MS, TREBUCHET_MS_BOLD)
            .forEach { fontName ->
                this.bitmapFonts[fontName] = BitmapFont(Gdx.files.internal("fonts/${fontName}.fnt"))
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