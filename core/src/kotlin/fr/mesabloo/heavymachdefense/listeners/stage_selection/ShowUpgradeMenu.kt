package fr.mesabloo.heavymachdefense.listeners.stage_selection

import aurelienribon.tweenengine.Timeline
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenCallback
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Circ
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.POSITION
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.SIZE

class ShowUpgradeMenu(
    private val tweenManager: TweenManager,
    private val controlsPane: Group,
    private val scrollPane: ScrollPane
) : ClickListener() {
    private companion object {
        const val OPEN_CLOSE_DURATION = 0.5f
    }

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (this.controlsPane.y < -1f) {
            // menu is closed, open it
            this.open()
        } else {
            // menu is already opened, close it
            this.close()
        }
    }

    private fun open() {
        Gdx.app.debug(this.javaClass.simpleName, "Opening upgrade panel")

        Timeline.createParallel()
            .push(
                Tween.to(this.controlsPane, POSITION, OPEN_CLOSE_DURATION)
                    .target(0f, 0f)
                    .ease(Circ.OUT)
            )
            .push(
                Tween.to(this.scrollPane, SIZE, OPEN_CLOSE_DURATION)
                    .target(this.scrollPane.width, this.scrollPane.height - 512f)
                    .ease(Circ.OUT)
            )
            .push(
                Tween.to(this.scrollPane, POSITION, OPEN_CLOSE_DURATION)
                    .target(this.scrollPane.x, this.scrollPane.y + 512f)
                    .ease(Circ.OUT)
            )
            .start(this.tweenManager)

        // NOTE: ideally, scrolling does not change from the bottom, instead of from the top, but oh well
        // scrollpanes are implemented with their origin on the top left instead of the bottom left, so I can't do anything about it
    }

    private fun close() {
        Gdx.app.debug(this.javaClass.simpleName, "Closing upgrade panel")

        this.scrollPane.setSmoothScrolling(false)
        Timeline.createParallel()
            .push(
                Tween.to(this.controlsPane, POSITION, OPEN_CLOSE_DURATION)
                    .target(0f, -512f)
                    .ease(Circ.OUT)
            )
            .push(
                Tween.to(this.scrollPane, SIZE, OPEN_CLOSE_DURATION)
                    .target(this.scrollPane.width, this.scrollPane.height + 512f)
                    .ease(Circ.OUT)
            )
            .push(
                Tween.to(this.scrollPane, POSITION, OPEN_CLOSE_DURATION)
                    .target(this.scrollPane.x, this.scrollPane.y - 512f)
                    .ease(Circ.OUT)
            )
            .setCallbackTriggers(TweenCallback.COMPLETE)
            .setCallback { _, _ -> this.scrollPane.setSmoothScrolling(true) }
            .start(this.tweenManager)
    }
}