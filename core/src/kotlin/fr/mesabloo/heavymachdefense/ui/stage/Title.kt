package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager

class Title(kind: StageAssetsManager.UI.TitleKind) : Image(TextureRegionDrawable(StageAssetsManager.UI.title(kind))) {
    var kind: StageAssetsManager.UI.TitleKind = kind
        set(value) = _setTitle(value)

    private fun _setTitle(title: StageAssetsManager.UI.TitleKind) {
        this.drawable = TextureRegionDrawable(StageAssetsManager.UI.title(title))
    }
}