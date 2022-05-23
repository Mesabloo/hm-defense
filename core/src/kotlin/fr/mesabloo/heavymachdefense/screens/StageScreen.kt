package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import fr.mesabloo.heavymachdefense.DEBUG
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.machine.MachineKind
import fr.mesabloo.heavymachdefense.entities.createBackground
import fr.mesabloo.heavymachdefense.entities.createCamera
import fr.mesabloo.heavymachdefense.entities.createMachine
import fr.mesabloo.heavymachdefense.entities.ui.createGameUI
import fr.mesabloo.heavymachdefense.events.MouseInputEvent
import fr.mesabloo.heavymachdefense.systems.MoveMachineSystem
import fr.mesabloo.heavymachdefense.systems.camera.MoveCameraSystem
import fr.mesabloo.heavymachdefense.systems.input.MouseInputSystem
import fr.mesabloo.heavymachdefense.systems.input.processor.MouseInputProcessor
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.systems.rendering.debug.DebugWorldSystem
import fr.mesabloo.heavymachdefense.world.GameWorld

/**
 * This is the base class for the main game.
 * This is where the game is played (spawning machines, etc.).
 */
class StageScreen(game: MainGame, private val number: Int) : AbstractScreen(game) {
    /**
     * The Box2D world in which physics take place.
     * We do not have any gravity (in fact, the gravity should be pushing perpendicular to both X and Y axes).
     */
    private val world = GameWorld()

    private val mux: InputMultiplexer = InputMultiplexer()

    /////////////////// INTERNAL ///////////////////////

    private val mouseInputSignal = Signal<MouseInputEvent>()

    init {
        world.engine.addSystem(MoveMachineSystem())
        world.engine.addSystem(MouseInputSystem(mouseInputSignal))
        world.engine.addSystem(MoveCameraSystem())
        ///// RENDERING
        //world.engine.addSystem(RenderPositionedTextures(world.batch))
        //world.engine.addSystem(RenderMachinesSystem(world.batch))
        if (DEBUG)
            world.engine.addSystem(DebugWorldSystem(world.world, world.camera))

        createCamera(world.engine, world.camera)
        createMachine(world.engine, world.world, MachineKind.RIFLE, 1)
        createBackground(world.engine, this.number, world.world)

        //////////////////////////

        this.ui.engine.addSystem(RenderPositionedTextures(ui.batch))
        createGameUI(this.ui.engine)
    }

    override fun setupInputProcessor() {
        Gdx.input.inputProcessor = this.mux
    }

    override fun show() {
        super.show()

        this.mux.addProcessor(MouseInputProcessor(this.mouseInputSignal))
    }

    override fun render(delta: Float) {
        this.world.render(delta)
        this.ui.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        this.world.resize(width, height)
        this.ui.resize(width, height)
    }

    override fun dispose() {
        this.world.dispose()
        this.ui.dispose()

        super.dispose()
    }
}