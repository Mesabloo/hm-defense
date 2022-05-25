package fr.mesabloo.heavymachdefense.ui.common

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.ui.Button

class BackButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.BACK, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.BACK, true))
    }
)