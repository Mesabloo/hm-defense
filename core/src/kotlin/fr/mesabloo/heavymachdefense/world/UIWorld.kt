package fr.mesabloo.heavymachdefense.world

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.mesabloo.heavymachdefense.internal.Batcher

const val UI_WIDTH = 768f // = 768f
const val UI_HEIGHT = 1024f // the game screen is actually twice as high, but we only show half of it

class UIWorld : Disposable {
    val camera: OrthographicCamera = OrthographicCamera(UI_WIDTH, UI_HEIGHT)
    private val viewport: FitViewport = FitViewport(UI_WIDTH, UI_HEIGHT, this.camera)

    val engine = PooledEngine()

    val batch = Batcher()

    init {
        this.camera.position.set(UI_WIDTH / 2, UI_HEIGHT / 2, 0f)
        this.camera.update()
    }

    fun show() {
        this.viewport.update(Gdx.graphics.width, Gdx.graphics.height, true)
    }

    fun resize(width: Int, height: Int) {
        this.viewport.update(width, height, true)
    }

    fun render(delta: Float) {
        this.camera.update()
        this.batch.projectionMatrix = this.camera.combined

        this.engine.update(delta)
        this.batch.flush()
    }

    override fun dispose() {
        this.batch.dispose()
    }
}