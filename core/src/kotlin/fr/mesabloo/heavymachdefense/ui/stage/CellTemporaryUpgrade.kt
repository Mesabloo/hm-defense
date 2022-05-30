package fr.mesabloo.heavymachdefense.ui.stage

import aurelienribon.tweenengine.Timeline
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.listeners.stage.TemporaryCellUpgrade
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor
import ktx.actors.alpha
import kotlin.reflect.KMutableProperty0

class CellTemporaryUpgrade(
    private val currentCells: KMutableProperty0<Long>,
    private val tempUpgradeCost: KMutableProperty0<Long>,
    private val tempUpgradeCount: KMutableProperty0<Long>
) : Group() {
    private val upgradeFlash = Image(stageAssetsManager.get(StageAssetsManager.UI.UPGRADE_CELL))
    private val upgradeCost = Label("0 cell", Label.LabelStyle().also {
        it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_12_WHITE]
        it.fontColor = Color.WHITE
    })
    private val tweenManager = TweenManager()

    init {
        this.touchable = Touchable.childrenOnly

        Tween.registerAccessor(Actor::class.java, ActorAccessor())

        this.addActor(this.upgradeFlash.also {
            it.setPosition(0f, 0f)
            it.alpha = 0f
            it.touchable = Touchable.enabled

            it.addListener(TemporaryCellUpgrade(this.currentCells, this.tempUpgradeCost, this.tempUpgradeCount))
        })
        this.addActor(this.upgradeCost.also {
            it.setAlignment(Align.bottomRight)
            it.setPosition(64f, 0f)
            it.width = 70f
        })
    }

    override fun act(delta: Float) {
        if (this.currentCells.get() < this.tempUpgradeCost.get()) {
            this.upgradeFlash.alpha = 0f

            this.tweenManager.killAll()
        } else if (!this.tweenManager.containsTarget(this.upgradeFlash)) {
            this.upgradeFlash.alpha = 1f

            Timeline.createSequence()
                .push(
                    Tween.set(this.upgradeFlash, ActorAccessor.OPACITY)
                        .target(1.0f)
                )
                .pushPause(1f)
                .push(
                    Tween.set(this.upgradeFlash, ActorAccessor.OPACITY)
                        .target(0.0f)
                )
                .pushPause(1f)
                .repeat(-1, 0f)
                .start(this.tweenManager)
        }

        this.tweenManager.update(delta)

        this.upgradeCost.setText("${this.tempUpgradeCost.get()} cell")

        super.act(delta)
    }
}