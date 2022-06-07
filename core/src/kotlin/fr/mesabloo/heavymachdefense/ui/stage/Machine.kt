package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.data.MachineKind
import fr.mesabloo.heavymachdefense.data.models.MachineModel
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

class Machine(kind: MachineKind, level: Int) : Group() {
    init {
        val oLevel = level.toString().padStart(2, '0')

        val model = MachineModel(kind.machineName, level)

        val body =
            Image(stageAssetsManager.unsafeRegion(StageAssetsManager.MACHINE_BODIES, "${kind.machineName}-$oLevel"))
        val weapon1 =
            Image(stageAssetsManager.unsafeRegion(StageAssetsManager.MACHINE_WEAPONS, "${kind.machineName}-$oLevel"))
        val weapon2 = Image(
            stageAssetsManager.unsafeRegion(StageAssetsManager.MACHINE_WEAPONS, "${kind.machineName}-$oLevel").also {
                it.flip(false, true)
            })

        // TODO: feet

        this.addActor(body.also {
            this.width = it.width
            this.height = it.height

            it.setPosition(0f, 0f)
        })
        this.addActor(weapon1.also {
            it.setPosition(
                body.width / 2f + model.leftWeaponOffset.first - it.width / 2f,
                body.height / 2f + model.leftWeaponOffset.second - it.height / 2f
            )
            it.zIndex = 5000
        })
        this.addActor(weapon2.also {
            it.setPosition(
                body.width / 2f + model.rightWeaponOffset.first - it.width / 2f,
                body.height / 2f + model.rightWeaponOffset.second - it.height / 2f
            )
            it.zIndex = 5000
        })
    }
}