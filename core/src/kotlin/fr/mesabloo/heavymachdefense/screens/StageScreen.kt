package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.AssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.RenderStageSystem
import ktx.ashley.entity
import ktx.ashley.plusAssign
import ktx.ashley.with
import ktx.box2d.createWorld

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

    /////////////////// INTERNAL ///////////////////////

    private val engine = PooledEngine()

    private val entities = mutableListOf<Entity>()

    init {
        //engine.addSystem(MoveMachineSystem())
        this.engine.addSystem(RenderStageSystem(this.batch))

        this.entities.add(engine.entity {
            with<TextureComponent> {
                texture = AssetsManager.textureFromAtlasRegion(AssetsManager.machineBodies, "rifle-01")
            }
            with<PositionComponent> {
                x = 50f
                y = 50f
                z = 10000f
            }
        })
    }

    override fun show() {
    }

    override fun render(deltaTime: Float) {
        this.engine.update(deltaTime)
    }

    override fun resize(p0: Int, p1: Int) {
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