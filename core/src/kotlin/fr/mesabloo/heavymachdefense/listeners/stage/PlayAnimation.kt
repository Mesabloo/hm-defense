package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.managers.animationManager
import fr.mesabloo.heavymachdefense.ui.common.AnimatedImage

class PlayAnimation(private val image: AnimatedImage) : ClickListener() {
    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        !event.isCancelled

    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
        val id = this.image.animationId

        animationManager.setCurrentKeyframe(id, 0)
        animationManager.resume(id)
    }
}