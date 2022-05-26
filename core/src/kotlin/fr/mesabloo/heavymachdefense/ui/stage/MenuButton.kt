package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager

class MenuButton : ImageButton(ImageButtonStyle().also {
    it.up = TextureRegionDrawable(StageAssetsManager.UI.button(StageAssetsManager.UI.ButtonKind.MENU, false))
    it.down = TextureRegionDrawable(StageAssetsManager.UI.button(StageAssetsManager.UI.ButtonKind.MENU, true))
})