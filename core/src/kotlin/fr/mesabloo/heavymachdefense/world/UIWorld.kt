package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.world

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ExtendViewport

const val UI_WIDTH = 768f // = 768f
const val UI_HEIGHT = 1024f // the game screen is actually twice as high, but we only show half of it

class UIWorld : Disposable {
    val camera: OrthographicCamera = OrthographicCamera(UI_WIDTH, UI_HEIGHT)
    private val viewport: ExtendViewport = ExtendViewport(UI_WIDTH, UI_HEIGHT, this.camera)

    val batch = SpriteBatch()

    val engine = PooledEngine()

    init {
        this.camera.position.set(UI_WIDTH / 2, UI_HEIGHT / 2, 0f)
        this.camera.update()
    }

    fun resize(width: Int, height: Int) {
        this.viewport.update(width, height, false)
    }

    fun render(deltaTime: Float) {
        this.camera.update()
        this.batch.projectionMatrix = this.camera.combined

        this.engine.update(deltaTime)
    }

    override fun dispose() {
        this.engine.removeAllSystems()
        this.engine.removeAllEntities()
        this.engine.clearPools()
    }
}