package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.ui.common

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array
import fr.mesabloo.heavymachdefense.managers.animationManager

open class AnimatedImage(frameDuration: Float, defaultKeyFrame: Int, keyframes: Array<TextureRegion>) : Image(keyframes[defaultKeyFrame]) {
    val animationId: Long = animationManager.newAnimation(frameDuration, keyframes)

    override fun act(delta: Float) {
        super.act(delta)

        this.drawable = TextureRegionDrawable(animationManager.update(this.animationId, delta))
    }
}