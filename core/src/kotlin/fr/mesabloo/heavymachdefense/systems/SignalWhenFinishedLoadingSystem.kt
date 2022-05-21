package fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.signals.Signal

class SignalWhenFinishedLoadingSystem(private val loadingDoneSignal: Signal<Unit>, private val finishedLoading: () -> Boolean) : EntitySystem() {
    private var alreadyFinished: Boolean = false

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        if (!this.alreadyFinished && this.finishedLoading()) {
            this.alreadyFinished = true
            this.loadingDoneSignal.dispatch(Unit)
        }
    }
}
