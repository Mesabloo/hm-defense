package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.ui.stage.slots.BuildSlot
import kotlin.reflect.KMutableProperty0

class BuildMachineIfPossible(private val slot: BuildSlot, private val cells: KMutableProperty0<Long>) :
    ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (!this.slot.isDisabled) {
            val currentCells = this.cells.get()

            this.cells.set(currentCells - this.slot.cellCost)

            Gdx.app.debug(this.javaClass.simpleName, "TODO: add machine to build queue")
        }
    }
}