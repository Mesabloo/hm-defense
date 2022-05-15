package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.DEBUG
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineKind
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.entities.createMachine
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.AssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.UpdatePositionFromBodySystem
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering.RenderMachinesSystem
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering.debug.DebugWorldSystem
import ktx.box2d.createWorld

const val VP_WIDTH = 512f
const val VP_HEIGHT = 1024f // the game screen is actually twice as high, but we only show half of it

/**
 * This is the base class for the main game.
 * This is where the game is played (spawning machines, etc.).
 */
class StageScreen(private val batch: SpriteBatch, private val number: Int) : Screen {
    /**
     * The Box2D world in which physics take place.
     * We do not have any gravity (in fact, the gravity should be pushing perpendicular to both X and Y axes).
     */
    private val world = createWorld()

    // TODO: make camera a component with a system `CameraSystem`
    private val camera: Camera = OrthographicCamera(VP_WIDTH, VP_HEIGHT)

    /////////////////// INTERNAL ///////////////////////

    private val engine = PooledEngine()

    private val entities = mutableListOf<Entity>()

    init {
        this.camera.position.set(VP_WIDTH / 2, VP_HEIGHT / 2, 0f)
        this.camera.update()

        //engine.addSystem(MoveMachineSystem())
        this.engine.addSystem(RenderMachinesSystem(this.batch))
        this.engine.addSystem(UpdatePositionFromBodySystem())
        if (DEBUG)
            this.engine.addSystem(DebugWorldSystem(this.world, this.camera))

        this.entities.add(createMachine(this.engine, this.world, MachineKind.RIFLE, 1))
    }

    override fun show() {

    }

    @Suppress("NAME_SHADOWING")
    override fun render(deltaTime: Float) {
        val deltaTime = if (deltaTime > 0.1f) 0.1f else deltaTime

        this.camera.update()
        this.batch.projectionMatrix = this.camera.combined

        this.batch.begin()
        this.batch.draw(AssetsManager.background(this.number, 1), 0f, 0f)
        this.batch.draw(AssetsManager.background(this.number, 2), 0f, 1024f)

        this.engine.update(deltaTime)
        this.batch.end()
    }

    override fun resize(width: Int, height: Int) {
        this.camera.update()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        this.engine.clearPools()
    }
}