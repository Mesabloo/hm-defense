package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.data.Builds
import fr.mesabloo.heavymachdefense.ui.stage.BuildMachineItem
import fr.mesabloo.heavymachdefense.ui.stage.BuildQueue
import fr.mesabloo.heavymachdefense.ui.stage.slots.BuildSlot
import fr.mesabloo.heavymachdefense.ui.stage.slots.MachineBuildSlot
import fr.mesabloo.heavymachdefense.ui.stage.slots.TurretBuildSlot
import kotlin.reflect.KMutableProperty0

class BuildMachineIfPossible(
    private val slot: BuildSlot,
    private val cells: KMutableProperty0<Long>,
    private val buildQueue: BuildQueue,
    private val builds: Builds,
    private val upgradeMenuShown: KMutableProperty0<Boolean>
) :
    ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (!this.slot.isDisabled && !this.upgradeMenuShown.get()) {
            val currentCells = this.cells.get()

            this.cells.set(currentCells - this.slot.cellCost)

            this.buildQueue.build(
                when (this.slot) {
                    is MachineBuildSlot -> BuildMachineItem(this.slot.kind, this.slot.level, this.builds)
                    is TurretBuildSlot -> TODO()
                    else -> TODO()
                }
            )
        }
    }
}