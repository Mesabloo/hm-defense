package fr.mesabloo.heavymachdefense.ui.stage.game

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Linear
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.ROTATION

class AllyBase(level: Int) :
    Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "base-${level.toString().padStart(2, '0')}"))
{

}

class EnemyBase : Group() {
    private val radar: Actor

    private val tweenManager = TweenManager()

    init {
        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ENEMY_BASE, "base-01")).also {
            it.setPosition(0f, 0f)

            this.width = it.width
            this.height = it.height
        })

        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ENEMY_BASE, "radar-01")).also {
            this.radar = it

            it.setOrigin(21f, 10f)
            it.setPosition(188f - it.originX, 83f - it.originY)
        })

        Tween.registerAccessor(Actor::class.java, ActorAccessor())
        Tween.to(this.radar, ROTATION, 0.60f)
            .target(360f)
            .ease(Linear.INOUT)
            .repeat(Tween.INFINITY, 0f)
            .start(this.tweenManager)
    }

    override fun act(delta: Float) {
        this.tweenManager.update(delta)

        super.act(delta)
    }
}