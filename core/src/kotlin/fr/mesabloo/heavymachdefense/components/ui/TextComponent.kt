package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.ashley.Mapper

class TextComponent(var message: String = ""): Component {
    lateinit var font: BitmapFont

    companion object: Mapper<TextComponent>()
}