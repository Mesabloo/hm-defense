package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

class MachSlot : Image(stageAssetsManager.get(StageAssetsManager.UI.MACH_SLOT)) {
    init {
        this.touchable = Touchable.disabled
    }
}