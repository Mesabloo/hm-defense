package fr.mesabloo.heavymachdefense.ui.common

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.ui.Button

class CancelButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.CANCEL, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.CANCEL, true))
    }
)