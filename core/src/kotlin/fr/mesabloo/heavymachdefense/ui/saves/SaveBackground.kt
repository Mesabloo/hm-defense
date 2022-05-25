package fr.mesabloo.heavymachdefense.ui.saves

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager

class SaveBackground(isFocused: Boolean) : Image(menuAssetsManager.getSlot(isFocused)) {
    fun setFocused(isFocused: Boolean) {
        this.drawable = TextureRegionDrawable(menuAssetsManager.getSlot(isFocused))
    }
}