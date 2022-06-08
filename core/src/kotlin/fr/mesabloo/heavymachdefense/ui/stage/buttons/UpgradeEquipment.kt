package fr.mesabloo.heavymachdefense.ui.stage.buttons

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

class UpgradeEquipment(kind: StageAssetsManager.UI.TitleKind) : ImageButton(ImageButtonStyle().also {
    it.up = TextureRegionDrawable(stageAssetsManager.unsafeRegion(StageAssetsManager.UI.UPGRADE_EQUIPMENT, when (kind) {
        StageAssetsManager.UI.TitleKind.BUILD_MACH -> "machine-normal"
        StageAssetsManager.UI.TitleKind.SPECIAL_ATTACK -> "special-normal"
    }))
    it.down = TextureRegionDrawable(stageAssetsManager.unsafeRegion(StageAssetsManager.UI.UPGRADE_EQUIPMENT, when (kind) {
        StageAssetsManager.UI.TitleKind.BUILD_MACH -> "machine-selected"
        StageAssetsManager.UI.TitleKind.SPECIAL_ATTACK -> "special-selected"
    }))
}) {
    var kind: StageAssetsManager.UI.TitleKind = kind
        set(value) {
            field = value
            changeButtonKind()
        }

    private fun changeButtonKind() {
        this.style.also {
            it.up = TextureRegionDrawable(stageAssetsManager.unsafeRegion(StageAssetsManager.UI.UPGRADE_EQUIPMENT, when (kind) {
                StageAssetsManager.UI.TitleKind.BUILD_MACH -> "machine-normal"
                StageAssetsManager.UI.TitleKind.SPECIAL_ATTACK -> "special-normal"
            }))
            it.down = TextureRegionDrawable(stageAssetsManager.unsafeRegion(StageAssetsManager.UI.UPGRADE_EQUIPMENT, when (kind) {
                StageAssetsManager.UI.TitleKind.BUILD_MACH -> "machine-selected"
                StageAssetsManager.UI.TitleKind.SPECIAL_ATTACK -> "special-selected"
            }))
        }
    }
}