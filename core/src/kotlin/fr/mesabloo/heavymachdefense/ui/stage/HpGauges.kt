package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import kotlin.properties.Delegates
import kotlin.reflect.KProperty0

class HpGaugesBackground : Image(stageAssetsManager.get(StageAssetsManager.UI.HP_GAUGE)) {
    init {
        this.touchable = Touchable.disabled
    }
}

class PlayerGauge(private val life: KProperty0<Long>) :
    Image(stageAssetsManager.get(StageAssetsManager.UI.PLAYER_HP_GAUGE)) {
    private val baseLife = life.get()
    private var assigned = false
    private var baseWidth by Delegates.vetoable(0f) { _, _, _ -> !this.assigned }

    override fun act(delta: Float) {
        super.act(delta)

        this.baseWidth = this.width
        this.assigned = true

        val newLife = this.life.get()
        val percentOfLifeLost = newLife.toFloat() / baseLife.toFloat()

        this.width = this.baseWidth * percentOfLifeLost
    }
}

class EnemyGauge(private val life: KProperty0<Long>) :
    Image(stageAssetsManager.get(StageAssetsManager.UI.ENEMY_HP_GAUGE)) {
    private val baseLife = life.get()
    private var assigned = false
    private var assigned2 = false
    private var baseWidth by Delegates.vetoable(0f) { _, _, _ -> !this.assigned }
    private var baseX by Delegates.vetoable(0f) { _, _, _ -> !this.assigned2 }

    override fun act(delta: Float) {
        super.act(delta)

        this.baseWidth = this.width
        this.assigned = true
        this.baseX = this.x
        this.assigned2 = true

        val newLife = this.life.get()
        val percentOfLifeLost = newLife.toFloat() / baseLife.toFloat()

        this.width = this.baseWidth * percentOfLifeLost
        this.x = this.baseX + this.baseWidth - this.baseWidth * percentOfLifeLost
    }
}

class HpGauges(playerLife: KProperty0<Long>, enemyLife: KProperty0<Long>) : Group() {
    init {
        this.addActor(HpGaugesBackground().also {
            this.width = it.width
            this.height = it.height
        })
        this.addActor(PlayerGauge(playerLife).also {
            it.setPosition(76f, 13f)
        })
        this.addActor(EnemyGauge(enemyLife).also {
            it.setPosition(272f, 13f)
        })
    }
}