package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.world

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ExtendViewport
import ktx.box2d.createWorld

const val WORLD_WIDTH = 512f // = 768f
const val WORLD_HEIGHT = 1024f // the game screen is actually twice as high, but we only show half of it

class GameWorld : Disposable {
    val world: World = createWorld()

    val camera: OrthographicCamera = OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT)
    private val viewport: ExtendViewport = ExtendViewport(WORLD_WIDTH + 256, WORLD_HEIGHT, this.camera)

    val batch = SpriteBatch()

    val engine = PooledEngine()

    init {
        this.camera.position.set(WORLD_WIDTH / 2 + 128, WORLD_HEIGHT / 2, 0f)
        this.camera.update()
    }

    fun resize(width: Int, height: Int) {
        this.viewport.update(width, height, false)
    }

    @Suppress("NAME_SHADOWING")
    fun render(deltaTime: Float) {
        val deltaTime = if (deltaTime > 0.1f) 0.1f else deltaTime

        this.camera.update()
        this.batch.projectionMatrix = this.camera.combined

        this.engine.update(deltaTime)
    }

    override fun dispose() {
        this.engine.removeAllSystems()
        this.engine.removeAllEntities()
        this.engine.clearPools()

        this.world.dispose()
    }
}