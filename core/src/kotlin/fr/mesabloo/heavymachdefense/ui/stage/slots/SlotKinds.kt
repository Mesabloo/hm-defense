package fr.mesabloo.heavymachdefense.ui.stage.slots

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.data.MachineSlot
import fr.mesabloo.heavymachdefense.data.TurretSlot
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

abstract class BuildSlot : Group() {
    init {
        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_SLOT_BACKGROUND)).also {
            this.width = it.width
            this.height = it.height

            it.setPosition(0f, 0f)
        })
        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_SLOT_FOREGROUND)).also {
            it.setPosition(-51f, -50f)
        })
    }
}

class MachineBuildSlot(slot: MachineSlot) : BuildSlot() {

}

class TurretBuildSlot(slot: TurretSlot) : BuildSlot() {

}