package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.data.UpgradeKind
import fr.mesabloo.heavymachdefense.data.Upgrades
import fr.mesabloo.heavymachdefense.listeners.stage_selection.UpgradeSelectedItem
import fr.mesabloo.heavymachdefense.ui.stage.upgrade_menu.*
import fr.mesabloo.heavymachdefense.world.UI_WIDTH

class UpgradeMenu(private val save: GameSave, private val upgrades: Upgrades, closeListener: ClickListener) : Group() {
    private val buttonGroup = ButtonGroup<AbstractUpgradeLevelButton>()
    var upgradeButton: UpgradeButton

    init {
        this.width = UI_WIDTH

        this.addActor(BackgroundLeft().also {
            it.setPosition(0f, 0f)
        })
        this.addActor(BackgroundRight().also {
            it.setPosition(256f, 0f)
        })

        this.addActor(CloseButton().also {
            it.setPosition(512f, 6f)
            it.addListener(closeListener)
        })
        this.addActor(UpgradeButton().also {
            upgradeButton = it

            it.setPosition(256f, 6f)
            it.isDisabled = true

            it.addListener(UpgradeSelectedItem(this.buttonGroup, this.save, this.upgrades))
        })

        val baseCannonMaxLevel = upgrades.base_cannon.size.toLong()
        val baseDefenseMaxLevel = upgrades.base_defense.size.toLong()
        val buildTimeMaxLevel = upgrades.build_time.size.toLong()
        val crResearchMaxLevel = upgrades.cr_research.size.toLong()
        val cellResearchMaxLevel = upgrades.cell_research.size.toLong()
        val cellStorageMaxLevel = upgrades.cell_storage.size.toLong()

        val baseCannonLevel =
            (save.mainUpgrades[UpgradeKind.BASE_CANNON]?.toLong()?.coerceIn(1..baseCannonMaxLevel) ?: 1) - 1
        val baseDefenseLevel =
            (save.mainUpgrades[UpgradeKind.BASE_DEFENSE]?.toLong()?.coerceIn(1..baseDefenseMaxLevel) ?: 1) - 1
        val buildTimeLevel =
            (save.mainUpgrades[UpgradeKind.BUILD_TIME]?.toLong()?.coerceIn(1..buildTimeMaxLevel) ?: 1) - 1
        val crResearchLevel =
            (save.mainUpgrades[UpgradeKind.CR_RESEARCH]?.toLong()?.coerceIn(1..crResearchMaxLevel) ?: 1) - 1
        val cellResearchLevel =
            (save.mainUpgrades[UpgradeKind.CELL_RESEARCH]?.toLong()?.coerceIn(1..cellResearchMaxLevel) ?: 1) - 1
        val cellStorageLevel =
            (save.mainUpgrades[UpgradeKind.CELL_STORAGE]?.toLong()?.coerceIn(1..cellStorageMaxLevel) ?: 1) - 1

        val currentAttack = upgrades.base_cannon[baseCannonLevel.toInt()].attack
        val nextAttack =
            upgrades.base_cannon[(baseCannonLevel + 1).coerceIn(0 until baseDefenseMaxLevel).toInt()].attack

        val currentDefense = upgrades.base_defense[baseDefenseLevel.toInt()].defense
        val nextDefense =
            upgrades.base_defense[(baseDefenseLevel + 1).coerceIn(0 until baseDefenseMaxLevel).toInt()].defense

        val currentSize = upgrades.cell_storage[cellStorageLevel.toInt()].storage
        val nextSize =
            upgrades.cell_storage[(cellStorageLevel + 1).coerceIn(0 until cellStorageMaxLevel).toInt()].storage

        ////////////////////////////////////////

        this.buttonGroup.setMinCheckCount(0)
        this.buttonGroup.setMaxCheckCount(1)
        this.buttonGroup.setUncheckLast(true)

        this.buttonGroup.add(
            AbstractUpgradeLevelButton(
                UpgradeKind.BASE_CANNON,
                upgrades.base_cannon[baseCannonLevel.toInt()].cost,
                Pair(baseCannonLevel + 1, baseCannonMaxLevel),
                Triple("ATK.", currentAttack, nextAttack - currentAttack)
            ).also {
                it.setPosition(256f, 422f)
            })
        this.buttonGroup.add(
            AbstractUpgradeLevelButton(
                UpgradeKind.BASE_DEFENSE,
                upgrades.base_defense[baseDefenseLevel.toInt()].cost,
                Pair(baseDefenseLevel + 1, baseDefenseMaxLevel),
                Triple("DEF.", currentDefense, nextDefense - currentDefense)
            ).also {
                it.setPosition(256f, 358f)
            })
        this.buttonGroup.add(
            AbstractUpgradeLevelButton(
                UpgradeKind.BUILD_TIME,
                upgrades.build_time[buildTimeLevel.toInt()].cost,
                Pair(buildTimeLevel + 1, buildTimeMaxLevel)
            ).also {
                it.setPosition(128f, 284f)
            })
        this.buttonGroup.add(
            AbstractUpgradeLevelButton(
                UpgradeKind.CR_RESEARCH,
                upgrades.cr_research[crResearchLevel.toInt()].cost,
                Pair(crResearchLevel + 1, crResearchMaxLevel)
            ).also {
                it.setPosition(128f, 220f)
            })
        this.buttonGroup.add(
            AbstractUpgradeLevelButton(
                UpgradeKind.CELL_RESEARCH,
                upgrades.cell_research[cellResearchLevel.toInt()].cost,
                Pair(cellResearchLevel + 1, cellResearchMaxLevel)
            ).also {
                it.setPosition(128f, 156f)
            })
        this.buttonGroup.add(
            AbstractUpgradeLevelButton(
                UpgradeKind.CELL_STORAGE,
                upgrades.cell_storage[cellStorageLevel.toInt()].cost,
                Pair(cellStorageLevel + 1, cellStorageMaxLevel),
                Triple("Size", currentSize, nextSize - currentSize)
            ).also {
                it.setPosition(128f, 92f)
            })

        this.buttonGroup.buttons.forEach(this::addActor)
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (this.parent.y < -1f) {
            this.buttonGroup.uncheckAll()
        }

        this.buttonGroup.buttons
            .forEach {
                it.isDisabled = it.price > this.save.credits
                if (it.isDisabled)
                    it.isChecked = false
            }

        this.upgradeButton.isDisabled = this.buttonGroup.allChecked.size == 0
    }
}