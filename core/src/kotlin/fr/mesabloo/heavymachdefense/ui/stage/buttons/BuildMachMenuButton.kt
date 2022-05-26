package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.ui.stage.buttons

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.ui.common.AnimatedImage
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager

class BuildMachMenuButton : AnimatedImage(0.065f, 0, Array<TextureRegion>(8).also {
    for (i in 0..7) {
        it.insert(i, StageAssetsManager.UI.unsafeAnimationTile(StageAssetsManager.UI.MENU_BUTTONS, "build-mach-$i"))
    }
})