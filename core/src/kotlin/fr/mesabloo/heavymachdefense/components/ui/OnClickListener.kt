package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.Mapper

class OnClickListener : Component {
    // to unproject the touched area into the UI world coordinates
    lateinit var viewport: Viewport
    lateinit var listener : () -> Unit

    companion object: Mapper<OnClickListener>()
}