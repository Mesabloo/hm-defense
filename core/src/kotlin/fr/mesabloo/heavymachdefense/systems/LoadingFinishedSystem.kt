package fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.signals.Signal

class LoadingFinishedSystem(loadingAnimationFinishedSignal: Signal<Unit>, loadingDoneSignal: Signal<Unit>, private val todo: () -> Unit): EntitySystem() {
    private var isLoadingFinished = false
    private var isAnimationFinished = false
    private var alreadyDone = false

    init {
        loadingDoneSignal.add { _, _ -> this.isLoadingFinished = true }
        loadingAnimationFinishedSignal.add { _, _ -> this.isAnimationFinished = true }
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        if (this.isLoadingFinished && this.isAnimationFinished && !this.alreadyDone) {
            this.alreadyDone = true
            this.todo()
        }
    }
}