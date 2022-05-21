package fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.signals.Signal

class LoadingFinishedSystem(loadingAnimationFinishedSignal: Signal<Unit>, private val todo: () -> Unit): EntitySystem() {
    private var isLoadingFinished = false
    private var alreadyDone = false

    init {
        loadingAnimationFinishedSignal.add { _, _ -> this.isLoadingFinished = true }
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        if (this.isLoadingFinished && !this.alreadyDone) {
            this.alreadyDone = true
            this.todo()
        }
    }
}