package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.Mapper

class TextureComponent(var texture: TextureRegion? = null) : Component {
    companion object: Mapper<TextureComponent>()
}