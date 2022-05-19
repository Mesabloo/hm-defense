package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable

class FontManager : Disposable {
    companion object {
        const val CREDITS: String = "credits"
        const val LEVEL: String = "level"
        const val MINERAL: String = "mineral"
        const val STAGE: String = "stage"

        const val TREBUCHET_MS: String = "trebuc"
        const val TREBUCHET_MS_BOLD: String = "trebucbd"
    }

    fun init() {
        listOf(CREDITS, LEVEL, MINERAL, STAGE)
            .forEach { fontName ->
                this.bitmapFonts[fontName] = BitmapFont(Gdx.files.internal("fonts/${fontName}.fnt"))
            }
        listOf(TREBUCHET_MS, TREBUCHET_MS_BOLD)
            .forEach { fontName ->
                val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/${fontName}.ttf"))
                val parameters = FreeTypeFontGenerator.FreeTypeFontParameter()
                parameters.size = 12

                this.bitmapFonts[fontName] = generator.generateFont(parameters)
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