package fr.mesabloo.heavymachdefense.components.saves

import com.badlogic.ashley.core.Component
import fr.mesabloo.heavymachdefense.saves.GameSave
import ktx.ashley.Mapper

class SaveComponent : Component {
    lateinit var save: GameSave
    var focused: Boolean = false

    companion object: Mapper<SaveComponent>()
}