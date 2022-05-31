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

class CancelButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.CANCEL, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.CANCEL, true))
    }
)

class CloseButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.CLOSE, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.CLOSE, true))
    }
)

class DeleteButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.DELETE, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.DELETE, true))
    }
)

class NewButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.NEW, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.NEW, true))
    }
)

class OkButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.OK, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.OK, true))
        it.disabled = TextureRegionDrawable(buttonAssetsManager.unsafeTexture("${Button.OK.str}-disabled"))
    }
)

class SupportButton : ImageButton(
    ImageButtonStyle().also {
        it.up = TextureRegionDrawable(buttonAssetsManager.texture(Button.SUPPORT, false))
        it.down = TextureRegionDrawable(buttonAssetsManager.texture(Button.SUPPORT, true))
    }
)