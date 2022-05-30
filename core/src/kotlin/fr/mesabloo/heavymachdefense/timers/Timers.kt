package fr.mesabloo.heavymachdefense.timers

import com.badlogic.gdx.utils.Timer

class CellMiningTimer : Timer() {
    var isStopped: Boolean = false
        private set
    var isPaused: Boolean = false
        private set

    fun pause() {
        if (!this.isStopped) {
            super.stop()

            this.isPaused = true
        }
    }

    fun resume() {
        if (!this.isStopped) {
            super.start()

            this.isPaused = false
        }
    }

    override fun stop() {
        if (!this.isPaused) {
            super.stop()

            this.isStopped = true
        }
    }

    override fun start() {
        if (!this.isPaused) {
            super.start()

            this.isStopped = false
        }
    }
}

val cellMiningTimer by lazy { CellMiningTimer() }