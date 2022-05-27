package fr.mesabloo.heavymachdefense.tweens

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.gdx.scenes.scene2d.Actor
import ktx.actors.alpha


class ActorAccessor : TweenAccessor<Actor> {
    companion object {
        const val POSITION = 1
        const val SCALE = 2
        const val OPACITY = 3
        const val SIZE = 4
    }

    override fun getValues(target: Actor, tweenType: Int, returnValues: FloatArray): Int =
        when (tweenType) {
            POSITION -> {
                returnValues[0] = target.x
                returnValues[1] = target.y

                2
            }
            SCALE -> {
                returnValues[0] = target.scaleX
                returnValues[1] = target.scaleY

                2
            }
            OPACITY -> {
                returnValues[0] = target.alpha

                1
            }
            SIZE -> {
                returnValues[0] = target.width
                returnValues[1] = target.height

                2
            }
            else -> -1
        }

    override fun setValues(target: Actor, tweenType: Int, newValues: FloatArray) =
        when (tweenType) {
            POSITION -> target.setPosition(newValues[0], newValues[1])
            SCALE -> target.setScale(newValues[0], newValues[1])
            OPACITY -> target.alpha = newValues[0]
            SIZE -> target.setSize(newValues[0], newValues[1])
            else -> {}
        }
}