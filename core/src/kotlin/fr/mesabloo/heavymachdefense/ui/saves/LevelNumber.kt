package fr.mesabloo.heavymachdefense.ui.saves

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.fontManager

class LevelNumber(nb: Int) : Label(nb.toString().padStart(2, '0'), Skin().also {
    val style = LabelStyle(fontManager.bitmapFonts[FontManager.CREDITS]!!, null)

    it.add("default", style)
}) {
    init {
        this.setAlignment(Align.center)
        this.touchable = Touchable.disabled
    }
}