package fr.mesabloo.heavymachdefense.systems.rendering.animation

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Quart
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.components.PositionAccessor
import fr.mesabloo.heavymachdefense.components.PositionAccessor.Companion.POSITION
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.animation.AmortizedTransformToComponent
import ktx.ashley.allOf
import ktx.ashley.get

class TransformAnimationSystem(
    private val animationFinishedSignal: Signal<Unit>
) :
    IteratingSystem(allOf(PositionComponent::class, AmortizedTransformToComponent::class).get()) {

    private val tweenManager = TweenManager()
    private var finished: Boolean = false

    init {
        Tween.registerAccessor(PositionComponent::class.java, PositionAccessor())
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        this.tweenManager.update(deltaTime)

        if (this.tweenManager.runningTweensCount == 0 && !this.finished) {
            this.animationFinishedSignal.dispatch(Unit)
            this.finished = true
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val amortizedTransform = entity[AmortizedTransformToComponent.mapper]!!
        val positionComponent = entity[PositionComponent.mapper]!!

        if (!this.tweenManager.containsTarget(positionComponent)) {
            Tween.to(positionComponent, POSITION, amortizedTransform.duration)
                .target(amortizedTransform.destinationX, amortizedTransform.destinationY)
                .ease(Quart.INOUT)
                .delay(amortizedTransform.delay)
                .start(this.tweenManager)
        }
    }
}