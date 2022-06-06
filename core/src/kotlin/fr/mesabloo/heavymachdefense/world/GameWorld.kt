package fr.mesabloo.heavymachdefense.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import fr.mesabloo.heavymachdefense.BG_BORDER
import fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.ifDebug
import fr.mesabloo.heavymachdefense.ui.stage.Terrain
import ktx.actors.contains
import ktx.box2d.createWorld

class GameWorld(private val terrain: Terrain) : Disposable {
    val world: World = createWorld()

    private val debugRenderer = Box2DDebugRenderer()

    private val bodies = Array<Body>(50)

    init {
        this.world.setContactListener(object : ContactListener {
            override fun beginContact(p0: Contact) {
                val bodyA = p0.fixtureA
                val bodyB = p0.fixtureB

                if ((bodyA.userData == BG_BORDER && !bodyB.isSensor) || (bodyB.userData == BG_BORDER && !bodyA.isSensor)) {
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

    fun render(deltaTime: Float) {
        this.world.step(1 / 60f, 6, 2)

        bodies.clear()
        this.world.getBodies(bodies)

        bodies.forEach { body ->
            val pos = body.position
            (body.userData as? Actor)?.also {
                it.setPosition(pos.x * PPM - it.width / 2f, pos.y * PPM - it.height / 2f)

                if (!this.terrain.contains(it)) {
                    this.terrain.addActor(it)
                }
            }
        }

        ifDebug {
            this.debugRenderer.render(this.world, this.terrain.stage.camera.combined.cpy().translate(128f, 180f, 0f))
        }
    }

    override fun dispose() {
        this.world.dispose()
    }
}