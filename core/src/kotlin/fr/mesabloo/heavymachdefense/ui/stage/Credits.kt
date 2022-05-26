package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.ui.Label
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import kotlin.reflect.KProperty0

class Credits(private val credits: KProperty0<Long>) :
    Label(credits.get().toString(), LabelStyle(fontManager.bitmapFonts[FontManager.CREDITS], null)) {
    override fun act(delta: Float) {
        super.act(delta)

        val oldWidth = this.width
        this.setText(credits.get().toString())
        val newWidth = this.width

        this.x -= oldWidth - newWidth
    }
}