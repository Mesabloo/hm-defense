package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array
import fr.mesabloo.heavymachdefense.DEBUG
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.TextureComponent
import ktx.ashley.allOf
import ktx.ashley.get

const val VP_WIDTH = 100f
const val VP_HEIGHT = 150f

class RenderStageSystem(private val batch: SpriteBatch) :
    IteratingSystem(allOf(TextureComponent::class, PositionComponent::class).get()) {
    private var renderingQueue = Array<Entity>()

    private val camera : Camera

    init {
        // TODO: create a camera
        this.camera = OrthographicCamera(VP_WIDTH, VP_HEIGHT)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        this.renderingQueue.sort { o1, o2 -> o1[PositionComponent.mapper]!!.z.compareTo(o2[PositionComponent.mapper]!!.z) }

        this.camera.update()

        this.batch.begin()
        this.batch.projectionMatrix = this.camera.combined

        for (e: Entity in renderingQueue) {
            val texture = e[TextureComponent.mapper]!!.texture
            val position = e[PositionComponent.mapper]!!

            when (texture) {
                null -> {
                    if (DEBUG)
                        Gdx.app.error("RenderStageSystem", "Texture not found (`null` unexpected) for entity $e")
                    continue
                }
                else -> this.batch.draw(texture, position.x, position.y)
            }
        }
        this.batch.end()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity != null) {
            this.renderingQueue.add(entity)
        }
    }
}