package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.ashley.Mapper

class TextComponent(var message: String = ""): Component {
    lateinit var font: BitmapFont
    var align: Long = BOTTOM_LEFT

    companion object: Mapper<TextComponent>() {
        const val LEFT_X = 0b1L
        const val RIGHT_X = LEFT_X shl 1
        const val BOTTOM_Y = RIGHT_X shl 1
        const val TOP_Y = BOTTOM_Y shl 1
        const val CENTER_X = TOP_Y shl 1
        const val CENTER_Y = CENTER_X shl 1

        // some defaults

        const val CENTER = CENTER_X or CENTER_Y
        const val TOP_LEFT = TOP_Y or LEFT_X
        const val TOP_RIGHT = TOP_Y or RIGHT_X
        const val BOTTOM_LEFT = BOTTOM_Y or LEFT_X
        const val BOTTOM_RIGHT = BOTTOM_Y or RIGHT_X
    }
}