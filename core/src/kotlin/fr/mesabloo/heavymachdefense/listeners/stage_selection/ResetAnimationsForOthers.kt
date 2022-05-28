package fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.managers.animationManager
import fr.mesabloo.heavymachdefense.ui.common.AnimatedImage

class ResetAnimationsForOthers(private val inGroup: Group, private val than: AnimatedImage) : ClickListener() {
    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        !event.isCancelled

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        this.inGroup.children
            .filter { it is AnimatedImage && it != than }
            .forEach {
                val id = (it as AnimatedImage).animationId
                animationManager.pause(id)
                animationManager.setCurrentKeyframe(id, 0)
            }
    }
}