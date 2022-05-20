package fr.mesabloo.heavymachdefense.world

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ExtendViewport
import fr.mesabloo.heavymachdefense.BG_BORDER
import ktx.box2d.createWorld

const val WORLD_WIDTH = 512f
const val WORLD_HEIGHT = 1024f // the game screen is actually twice as high, but we only show half of it

class GameWorld : Disposable {
    val world: World = createWorld()

    val camera: OrthographicCamera = OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT) //OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT)
    private val viewport: ExtendViewport = ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, this.camera)

    val batch = SpriteBatch()

    val engine = PooledEngine()

    init {
        this.camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0f)
        this.camera.update()

        this.world.setContactListener(object: ContactListener {
            override fun beginContact(p0: Contact) {
                val bodyA = p0.fixtureA
                val bodyB = p0.fixtureB

                if (bodyA.userData == BG_BORDER || bodyB.userData == BG_BORDER) {
                    Gdx.app.debug(this.javaClass.simpleName, "Contact with border detected")
                }
            }

            override fun endContact(p0: Contact?) {

            }

            override fun preSolve(p0: Contact?, p1: Manifold?) {

            }

            override fun postSolve(p0: Contact?, p1: ContactImpulse?) {

            }
        })
    }

    fun resize(width: Int, height: Int) {
        this.viewport.update(width, height, false)
    }

    fun render(deltaTime: Float) {
        this.camera.update()
        this.batch.projectionMatrix = this.camera.combined

        this.engine.update(deltaTime)
        this.world.step(1 / 60f, 6, 2)
    }

    override fun dispose() {
        this.engine.removeAllSystems()
        this.engine.removeAllEntities()
        this.engine.clearPools()

        this.world.dispose()
    }
}