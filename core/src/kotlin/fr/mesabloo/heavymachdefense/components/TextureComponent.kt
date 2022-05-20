package fr.mesabloo.heavymachdefense.components

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

    companion object: Mapper<TextureComponent>()
}