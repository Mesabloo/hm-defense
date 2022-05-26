package fr.mesabloo.heavymachdefense.ui.stage.buttons

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager

class BaseUpgradeButton : ImageButton(ImageButtonStyle().also {
    it.up = TextureRegionDrawable(StageAssetsManager.UI.button(StageAssetsManager.UI.ButtonKind.BASE_UPGRADE, false))
    it.down = TextureRegionDrawable(StageAssetsManager.UI.button(StageAssetsManager.UI.ButtonKind.BASE_UPGRADE, true))
})