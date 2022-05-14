package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import ktx.ashley.Mapper

class TextureComponent(var texture: Texture? = null) : Component {
    companion object: Mapper<TextureComponent>()
}