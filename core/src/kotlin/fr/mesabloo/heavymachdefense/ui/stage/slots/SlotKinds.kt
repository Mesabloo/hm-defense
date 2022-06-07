package fr.mesabloo.heavymachdefense.ui.stage.slots

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import fr.mesabloo.heavymachdefense.data.Builds
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.data.MachineSlot
import fr.mesabloo.heavymachdefense.data.TurretSlot
import fr.mesabloo.heavymachdefense.entities.buildMachineTemplate
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import kotlin.reflect.KProperty0

abstract class BuildSlot(protected val cellCounter: KProperty0<Long>) : Group() {
    var isDisabled: Boolean = false

    private val cover = Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_SLOT_COVER))

    var cellCost: Int = 0
        protected set

    init {
        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_SLOT_BACKGROUND)).also {
            this.width = it.width
            this.height = it.height

            it.setPosition(0f, 0f)
        })

        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_SLOT_FOREGROUND)).also {
            it.setPosition(-51f, -50f)

            it.touchable = Touchable.enabled
        })

        this.addActor(this.cover)
        this.cover.setPosition(-51f, -50f)
    }

    override fun act(delta: Float) {
        this.isDisabled = this.cellCounter.get() < this.cellCost
        this.cover.zIndex = if (this.isDisabled) 50000000 else 0

        super.act(delta)
    }
}

class MachineBuildSlot(
    private val slot: MachineSlot,
    private val save: GameSave,
    private val builds: Builds,
    cellCounter: KProperty0<Long>
) : BuildSlot(cellCounter) {
    var level: Int = (save.machineUpgrades[slot.kind] ?: 1).coerceIn(1..10)
        set(value) {
            field = value.coerceIn(1..10)
            updateSlot()
        }

    private var machine: Actor
    private val cell: Actor

    init {
        this.machine = buildMachineTemplate(this.slot.kind, this.level)

        this.addActor(Label(slot.kind.machineName, Label.LabelStyle().also {
            it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_11_WHITE]
            it.fontColor = Color.WHITE
        }).also {
            it.setPosition(33f - it.prefWidth / 2f, -17f)

            it.touchable = Touchable.disabled
        })

        this.addActor(Label(this.cellCost.toString(), Label.LabelStyle().also {
            it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_12_WHITE]
            it.fontColor = Color.WHITE
        }).also {
            this.cell = it
        })

        this.updateSlot()
    }

    private fun updateSlot() {
        if (this.machine.hasParent()) {
            this.machine.remove()

            this.machine = buildMachineTemplate(this.slot.kind, this.level)
        }

        this.addActor(this.machine)

        this.cellCost = builds.machines[Pair(this.slot.kind, level)]?.cellCost ?: 9999

        this.machine.rotation = 90f
        this.machine.setPosition(80f - this.machine.width / 2f, 42f - this.machine.height / 2f)
        this.machine.touchable = Touchable.disabled

        (this.cell as Label).setText(this.cellCost.toString())
        this.cell.setPosition(-28f - this.cell.width / 2f, 51f)
        this.cell.touchable = Touchable.disabled
    }
}

class TurretBuildSlot(slot: TurretSlot, save: GameSave, builds: Builds, cellCounter: KProperty0<Long>) :
    BuildSlot(cellCounter)