package fr.mesabloo.heavymachdefense.world

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.mesabloo.heavymachdefense.DEBUG
import fr.mesabloo.heavymachdefense.internal.Batcher

const val UI_WIDTH = 768f
const val UI_HEIGHT = 1024f // the game screen is actually twice as high, but we only show half of it

class UIWorld : Stage(FitViewport(UI_WIDTH, UI_HEIGHT)) {
    private val camera: OrthographicCamera = OrthographicCamera(UI_WIDTH, UI_HEIGHT)

    val batch = Batcher()

    init {
        this.viewport.camera = this.camera

        this.camera.position.set(UI_WIDTH / 2, UI_HEIGHT / 2, 0f)
        this.camera.update()

        if (DEBUG) this.isDebugAll = true
    }

    fun resize(width: Int, height: Int) {
        this.viewport.update(width, height, true)
    }

    override fun draw() {
        super.draw()

        this.camera.update()
        this.batch.projectionMatrix = this.camera.combined

        this.batch.flush()
    }

    override fun dispose() {
        super.dispose()

        this.batch.dispose()
    }
}