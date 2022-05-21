package fr.mesabloo.heavymachdefense.components

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

class PositionComponent(
    var x: Float = 0f,
    var y: Float = 0f,
    var z: Float = 0f
) : Component {
    companion object : Mapper<PositionComponent>()
}

class PositionAccessor : TweenAccessor<PositionComponent> {
    companion object {
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