package fr.mesabloo.heavymachdefense.ui.start

import aurelienribon.tweenengine.Timeline
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StartAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.startAssetsManager
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.OPACITY
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH

class TapToStart : Image(startAssetsManager.texture(StartAssetsManager.TAP_TO_START)) {
    private val tweenManager = TweenManager()

    init {
        this.setPosition(UI_WIDTH / 2f - this.width / 2f, UI_HEIGHT / 2.25f - this.height / 2f)
        this.touchable = Touchable.disabled

        /////////////////////////////////////////////

        val appear: Tween = Tween.to(this, OPACITY, 0f)
            .target(1f)
        val disappear: Tween = Tween.to(this, OPACITY, 0f)
            .target(0f)

        Timeline.createSequence()
            .push(appear)
            .pushPause(1f)
            .push(disappear)
            .repeat(-1, 1f)
            .start(this.tweenManager)
    }

    override fun act(delta: Float) {
        super.act(delta)

        this.tweenManager.update(delta)
    }
}