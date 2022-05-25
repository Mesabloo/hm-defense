package fr.mesabloo.heavymachdefense.ui.saves

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import ktx.graphics.color

class Name(name: String) : Label(name, Skin().also {
    val style = LabelStyle(fontManager.bitmapFonts[FontManager.TREBUCHET_MS_20_BLUE]!!, color(0.478f, 1.000f, 0.933f))

    it.add("default", style)
}) {
    init {
        this.setAlignment(Align.center)
        this.touchable = Touchable.disabled
    }
}