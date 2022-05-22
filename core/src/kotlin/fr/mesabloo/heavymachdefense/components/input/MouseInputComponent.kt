package fr.mesabloo.heavymachdefense.components.input

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import ktx.ashley.Mapper
import ktx.math.vec2

/**
 * Holds information about any interesting mouse input which can happen during the game.
 */
class MouseInputComponent : Component {
    /**
     * Is the left button currently pressed?
     */
    var leftClick: Boolean = false

    /**
     * Is the right button currently pressed?
     */
    var rightClick: Boolean = false

    /**
     * How much to scroll on the X axis.
     * A fling gesture is also registered as scrolling.
     */
    var scrollX: Float = 0f

    /**
     * How much to scroll on the Y axis.
     * A fling gesture is also registered as scrolling.
     */
    var scrollY: Float = 0f

    /**
     * Where was the last click done (in screen coordinates)?
     *
     * This needs to be converted back to world-local coordinates
     * (either using [com.badlogic.gdx.utils.viewport.Viewport.unproject] or [com.badlogic.gdx.graphics.Camera.unproject])
     * in order to be usable in any input-consuming systems.
     */
    var clickPosition: Vector2 = vec2(0f, 0f)

    companion object: Mapper<MouseInputComponent>()
}