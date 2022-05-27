package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.data.UpgradeKind
import fr.mesabloo.heavymachdefense.ui.stage.upgrade_menu.*
import fr.mesabloo.heavymachdefense.world.UI_WIDTH

class UpgradeMenu(private val save: GameSave, closeListener: ClickListener) : Group() {
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
            it.setPosition(256f, 6f)
            it.isDisabled = true
        })

        this.addActor(
            AbstractUpgradeLevelButton(
                UpgradeKind.BASE_CANNON,
                200,
                Pair(1, 7),
                Triple("ATK.", 46, 14)
            ).also {
                it.setPosition(256f, 422f)

                it.isDisabled = it.price > this.save.credits
            })
        this.addActor(AbstractUpgradeLevelButton(UpgradeKind.BASE_DEFENSE, 200, Pair(1, 7)).also {
            it.setPosition(256f, 358f)

            it.isDisabled = it.price > this.save.credits
        })
        this.addActor(AbstractUpgradeLevelButton(UpgradeKind.BUILD_TIME, 5000, Pair(2, 10)).also {
            it.setPosition(128f, 284f)

            it.isDisabled = it.price > this.save.credits
        })
        this.addActor(AbstractUpgradeLevelButton(UpgradeKind.CR_RESEARCH, 75000, Pair(8, 10)).also {
            it.setPosition(128f, 220f)

            it.isDisabled = it.price > this.save.credits
        })
        this.addActor(AbstractUpgradeLevelButton(UpgradeKind.CELL_RESEARCH, 999999, Pair(1, 10)).also {
            it.setPosition(128f, 156f)

            it.isDisabled = it.price > this.save.credits
        })
        this.addActor(
            AbstractUpgradeLevelButton(
                UpgradeKind.CELL_STORAGE,
                12300,
                Pair(4, 10),
                Triple("Size", 2900, 500)
            ).also {
                it.setPosition(128f, 92f)

                it.isDisabled = it.price > this.save.credits
            })
    }
}