package fr.mesabloo.heavymachdefense.components.ui

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.Mapper

/**
 * A very simple component containing an action to execute on click.
 */
class OnClickListener : Component {
    /**
     * The [Viewport] used to un-project the screen coordinates in the
     * [fr.mesabloo.heavymachdefense.components.input.MouseInputComponent] to world-local coordinates.
     *
     * This allows checking whether we actually clicked the button or not (if the click is situated inside the
     * bounding box of the button).
     */
    lateinit var viewport: Viewport

    /**
     * An action to execute when the UI entity it is linked to has just been clicked.
     * This will not be fired multiple times if the left button is held.
     */
    lateinit var listener : () -> Unit

    companion object: Mapper<OnClickListener>()
}