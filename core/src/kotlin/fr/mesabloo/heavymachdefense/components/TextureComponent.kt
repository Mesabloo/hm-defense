package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.Mapper
import kotlin.properties.Delegates

class TextureComponent : Component {
    var texture: TextureRegion? = null
    var width by Delegates.notNull<Float>()
    var height by Delegates.notNull<Float>()

    companion object: Mapper<TextureComponent>()
}