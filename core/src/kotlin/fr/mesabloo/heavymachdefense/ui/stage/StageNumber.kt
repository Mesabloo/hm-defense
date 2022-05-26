package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.ui.Label
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.fontManager

class StageNumber(level: Int) : Label(level.toString(), LabelStyle(fontManager.bitmapFonts[FontManager.STAGE], null))