package fr.mesabloo.heavymachdefense.ui.saves

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import java.text.SimpleDateFormat

class Date(date: java.util.Date) : Label(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date), Skin().also {
    val style = LabelStyle(fontManager.bitmapFonts[FontManager.SET01B]!!, null)

    it.add("default", style)
}) {
    init {
        this.setAlignment(Align.center)
        this.touchable = Touchable.disabled
    }
}