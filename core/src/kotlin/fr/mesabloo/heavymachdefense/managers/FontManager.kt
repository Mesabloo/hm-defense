package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable

class FontManager : Disposable {
    companion object {
        const val CREDITS: String = "credits"
        const val LEVEL: String = "level"
        const val MINERAL: String = "mineral"
        const val STAGE: String = "stage"
    }

    fun init() {
        listOf(CREDITS, LEVEL, MINERAL, STAGE)
            .forEach { fontName ->
                this.fonts[fontName] = BitmapFont(Gdx.files.internal("fonts/${fontName}.fnt"))
            }
    }

    override fun dispose() {
        for ((_, value) in this.fonts) {
            value.dispose()
        }
        this.fonts.clear()
    }

    /////////////////// INTERNAL ////////////////////

    val fonts: MutableMap<String, BitmapFont> = mutableMapOf()
}

val fontManager by lazy { FontManager() }