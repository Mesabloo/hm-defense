package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.ui.common.AnimatedImage
import fr.mesabloo.heavymachdefense.managers.animationManager

class PlayAnimation(private val image: AnimatedImage) : ClickListener() {
    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean = true

    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
        Gdx.app.debug(this.javaClass.simpleName, "Restarting animation (id ${this.image.animationId})")

        val id = this.image.animationId

        animationManager.setCurrentKeyframe(id, 0)
        animationManager.resume(id)

        event.handle()
    }
}