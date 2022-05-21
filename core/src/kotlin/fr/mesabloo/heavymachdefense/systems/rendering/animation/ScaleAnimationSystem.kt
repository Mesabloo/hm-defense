package fr.mesabloo.heavymachdefense.systems.rendering.animation

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Quart
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal
import com.badlogic.ashley.systems.IteratingSystem
import fr.mesabloo.heavymachdefense.components.ScaleAccessor
import fr.mesabloo.heavymachdefense.components.ScaleAccessor.Companion.SCALE
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.animation.ScaleComponent
import ktx.ashley.allOf
import ktx.ashley.get

class ScaleAnimationSystem(private val animationFinishedSignal: Signal<Unit>) :
    IteratingSystem(allOf(ScaleComponent::class, TextureComponent::class).get()) {
    private val tweenManager = TweenManager()
    private var finished: Boolean = false

    init {
        Tween.registerAccessor(TextureComponent::class.java, ScaleAccessor())
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        this.tweenManager.update(deltaTime)

        if (this.tweenManager.runningTweensCount == 0 && !this.finished) {
            this.finished = true
            this.animationFinishedSignal.dispatch(Unit)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val scaleComponent = entity[ScaleComponent.mapper]!!
        val textureComponent = entity[TextureComponent.mapper]!!

        if (!this.tweenManager.containsTarget(textureComponent)) {
            Tween.to(textureComponent, SCALE, scaleComponent.duration)
                .ease(Quart.OUT)
                .delay(scaleComponent.delay)
                .target(scaleComponent.destinationX, scaleComponent.destinationY)
                .start(this.tweenManager)
        }
    }
}