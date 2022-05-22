package fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import ktx.ashley.Mapper

/**
 * A stateless component to easily identify sprites belonging to the loading screen.
 */
class LoadingPartComponent : Component {
    companion object: Mapper<LoadingPartComponent>()
}