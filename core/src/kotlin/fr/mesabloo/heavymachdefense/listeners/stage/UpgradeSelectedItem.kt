package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.data.UpgradeKind
import fr.mesabloo.heavymachdefense.data.Upgrades
import fr.mesabloo.heavymachdefense.ui.stage.upgrade_menu.AbstractUpgradeLevelButton
import kotlin.math.min

class UpgradeSelectedItem(
    private val buttonGroup: ButtonGroup<AbstractUpgradeLevelButton>,
    private val save: GameSave,
    private val upgrades: Upgrades
) : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        val selected = this.buttonGroup.checked
        if (selected != null) {
            // if the button is disabled, we cannot upgrade
            if (selected.isDisabled) {
                selected.isChecked = false
                return
            }

            val maxLevel = when (selected.kind) {
                UpgradeKind.BASE_CANNON -> this.upgrades.base_cannon.size
                UpgradeKind.BASE_DEFENSE -> this.upgrades.base_defense.size
                UpgradeKind.BUILD_TIME -> this.upgrades.build_time.size
                UpgradeKind.CELL_STORAGE -> this.upgrades.cell_storage.size
                UpgradeKind.CELL_RESEARCH -> this.upgrades.cell_research.size
                UpgradeKind.CR_RESEARCH -> this.upgrades.cr_research.size
                else -> throw IllegalArgumentException()
            }

            val currentLevel = this.save.mainUpgrades[selected.kind]?.coerceIn(1..maxLevel) ?: 1
            val nextLevel = min(currentLevel + 1, maxLevel)

            val upgradeCost = when (selected.kind) {
                UpgradeKind.BASE_CANNON -> this.upgrades.base_cannon[currentLevel - 1].cost
                UpgradeKind.BASE_DEFENSE -> this.upgrades.base_defense[currentLevel - 1].cost
                UpgradeKind.BUILD_TIME -> this.upgrades.build_time[currentLevel - 1].cost
                UpgradeKind.CELL_STORAGE -> this.upgrades.cell_storage[currentLevel - 1].cost
                UpgradeKind.CELL_RESEARCH -> this.upgrades.cell_research[currentLevel - 1].cost
                UpgradeKind.CR_RESEARCH -> this.upgrades.cr_research[currentLevel - 1].cost
                else -> 0
            }

            // NOTE: in case the upgrade cost is 0 (meaning there are no more upgrades)
            // just stop
            //
            // Same applies when the currentLevel is the maxLevel
            if (upgradeCost == 0L || currentLevel == maxLevel)
                return

            this.save.credits -= upgradeCost
            this.save.mainUpgrades[selected.kind] = nextLevel

            selected.level = Pair(nextLevel.toLong(), maxLevel.toLong())
            when (selected.kind) {
                UpgradeKind.BASE_CANNON -> {
                    val currentAtk = this.upgrades.base_cannon[nextLevel - 1].attack
                    val nextAtk = this.upgrades.base_cannon[nextLevel.coerceIn(0 until maxLevel)].attack

                    selected.price = this.upgrades.base_cannon[nextLevel - 1].cost
                    selected.addition = Triple("ATK.", currentAtk, nextAtk - currentAtk)
                }
                UpgradeKind.BASE_DEFENSE -> {
                    val currentDef = this.upgrades.base_defense[nextLevel - 1].defense
                    val nextDef = this.upgrades.base_defense[nextLevel.coerceIn(0 until maxLevel)].defense

                    selected.price = this.upgrades.base_defense[nextLevel - 1].cost
                    selected.addition = Triple("DEF.", currentDef, nextDef - currentDef)
                }
                UpgradeKind.BUILD_TIME -> {
                    selected.price = this.upgrades.build_time[nextLevel - 1].cost
                }
                UpgradeKind.CELL_STORAGE -> {
                    val currentSize = this.upgrades.cell_storage[nextLevel - 1].storage
                    val nextSize = this.upgrades.cell_storage[nextLevel.coerceIn(0 until maxLevel)].storage

                    selected.price = this.upgrades.cell_storage[nextLevel - 1].cost
                    selected.addition = Triple("Size", currentSize, nextSize - currentSize)
                }
                UpgradeKind.CELL_RESEARCH -> {
                    selected.price = this.upgrades.cell_research[nextLevel - 1].cost
                }
                UpgradeKind.CR_RESEARCH -> {
                    selected.price = this.upgrades.cr_research[nextLevel - 1].cost
                }
                else -> {
                }
            }

//            this.buttonGroup.buttons
//                .forEach {
//                    it.isDisabled = it.price > this.save.credits
//                    if (it.isDisabled)
//                        it.isChecked = false
//                }
        }
    }
}