package fr.mesabloo.heavymachdefense.ui.saves

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.MenuAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH

class Background : Image(menuAssetsManager.get(MenuAssetsManager.BACKGROUND)) {
    init {
        this.setSize(UI_WIDTH, UI_HEIGHT)
        this.setPosition(0f, 0f)
    }
}