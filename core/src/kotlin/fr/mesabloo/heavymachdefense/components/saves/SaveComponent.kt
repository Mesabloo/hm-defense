package fr.mesabloo.heavymachdefense.components.saves

import com.badlogic.ashley.core.Component
import fr.mesabloo.heavymachdefense.data.GameSave
import ktx.ashley.Mapper

/**
 * A component storing a given [GameSave].
 * It is only used in the main menu to show them.
 */
class SaveComponent : Component {
    /**
     * The [GameSave] it is associated to.
     */
    lateinit var save: GameSave

    /**
     * Is it currently focused in the menu?
     */
    var focused: Boolean = false

    companion object: Mapper<SaveComponent>()
}