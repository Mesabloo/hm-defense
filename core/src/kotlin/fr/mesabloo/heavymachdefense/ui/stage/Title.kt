package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager

class Title(title: StageAssetsManager.UI.TitleKind) : Image(TextureRegionDrawable(StageAssetsManager.UI.title(title))) {
    var title: StageAssetsManager.UI.TitleKind = StageAssetsManager.UI.TitleKind.BUILD_MACH
        set(value) = _setTitle(value)

    private fun _setTitle(title: StageAssetsManager.UI.TitleKind) {
        this.drawable = TextureRegionDrawable(StageAssetsManager.UI.title(title))
    }
}