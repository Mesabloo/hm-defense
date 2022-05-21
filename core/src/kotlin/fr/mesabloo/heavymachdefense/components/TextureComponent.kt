package fr.mesabloo.heavymachdefense.components

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.Mapper
import kotlin.properties.Delegates

class TextureComponent : Component {
    var texture: TextureRegion? = null
        set(value) {
            if (value != null) {
                field = value
                this.width = value.regionWidth.toFloat()
                this.height = value.regionHeight.toFloat()
            }
        }
    var width by Delegates.notNull<Float>()
    var height by Delegates.notNull<Float>()
    var opacity: Float = 1f
    var scaleX: Float = 1f
    var scaleY: Float = 1f

    companion object: Mapper<TextureComponent>()
}

class ScaleAccessor : TweenAccessor<TextureComponent> {
    companion object {
        const val SCALE = 2
    }

    override fun getValues(target: TextureComponent, tweenType: Int, returnValues: FloatArray): Int =
        when (tweenType) {
            SCALE -> {
                returnValues[0] = target.scaleX
                returnValues[1] = target.scaleY

                2
            }
            else -> {
                assert(false)
                -1
            }
        }

    override fun setValues(target: TextureComponent, tweenType: Int, newValues: FloatArray) =
        when (tweenType) {
            SCALE -> {
                target.scaleX = newValues[0]
                target.scaleY = newValues[1]
            }
            else -> {
                assert(false)
            }
        }
}