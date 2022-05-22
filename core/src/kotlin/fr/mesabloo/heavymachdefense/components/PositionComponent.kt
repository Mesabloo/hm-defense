package fr.mesabloo.heavymachdefense.components

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

/**
 * A position is a triplet containing the X and Y positions (no unit given) and a Z-index which is used
 * strictly for drawing onto a [fr.mesabloo.heavymachdefense.internal.Batcher].
 *
 * @property x The position on the X axis
 * @property y The position on the Y axis
 * @property zIndex The Z-index
 */
class PositionComponent(
    var x: Float = 0f,
    var y: Float = 0f,
    var zIndex: Int = 0
) : Component {
    companion object : Mapper<PositionComponent>()
}

/**
 * A very simple accessor used by the Tween engine to manipulate the position.
 */
class PositionAccessor : TweenAccessor<PositionComponent> {
    companion object {
        /**
         * The identifier for animations using this accessor.
         */
        const val POSITION: Int = 1
    }

    override fun getValues(target: PositionComponent, tweenType: Int, returnValues: FloatArray): Int =
        when (tweenType) {
            POSITION -> {
                returnValues[0] = target.x
                returnValues[1] = target.y

                2
            }
            else -> {
                assert(false)
                -1
            }
        }

    override fun setValues(target: PositionComponent, tweenType: Int, newValues: FloatArray) =
        when (tweenType) {
            POSITION -> {
                target.x = newValues[0]
                target.y = newValues[1]
            }
            else -> {
                assert(false)
            }
        }
}