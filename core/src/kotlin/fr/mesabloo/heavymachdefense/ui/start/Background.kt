package fr.mesabloo.heavymachdefense.ui.start

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StartAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.startAssetsManager
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH

class Background : Image(startAssetsManager.texture(StartAssetsManager.TITLE_BACKGROUND)) {
    init {
        this.setSize(UI_WIDTH, UI_HEIGHT)
        this.setPosition(0f, 0f)
    }
}