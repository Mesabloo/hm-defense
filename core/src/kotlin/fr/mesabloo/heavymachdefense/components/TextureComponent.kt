package fr.mesabloo.heavymachdefense.components

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.Mapper
import kotlin.properties.Delegates

/**
 * A component holding a [TextureRegion] (which may or may not be displayed on the screen).
 */
class TextureComponent : Component {
    /**
     * The [TextureRegion] encapsulated, most likely retrieved from an asset manager.
     */
    var texture: TextureRegion? = null
        /**
         * Set the field [texture] as well as [width] and [height] to the texture's size.
         */
        set(value) {
            if (value != null) {
                field = value
                this.width = value.regionWidth.toFloat()
                this.height = value.regionHeight.toFloat()
            }
        }

    /**
     * The width of the texture, which can be manually tweaked.
     */
    var width by Delegates.notNull<Float>()

    /**
     * The height of the texture, which can be manually tweaked.
     */
    var height by Delegates.notNull<Float>()

    /**
     * The opacity with which to display the texture, between `0f` (transparent) and `1f` (fully opaque).
     */
    var opacity: Float = 1f

    /**
     * How much to scale the texture on the X axis.
     */
    var scaleX: Float = 1f

    /**
     * How much to scale the texture on the Y axis.
     */
    var scaleY: Float = 1f

    companion object: Mapper<TextureComponent>()
}

/**
 * A [TweenAccessor] to manipulate the scale of the texture.
 */
class ScaleAccessor : TweenAccessor<TextureComponent> {
    companion object {
        /**
         * The unique identifier for this accessor.
         */
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