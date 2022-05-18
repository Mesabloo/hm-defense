package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Transform
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.MachinePart
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.MachAssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.machAssetsManager
import ktx.ashley.allOf
import ktx.ashley.get

class RenderMachinesSystem(private val batch: SpriteBatch) : IteratingSystem(
    allOf(
        MachineComponent::class
    ).get()
) {
    private var renderingQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val renderingQueue = this.renderingQueue.toMutableList()
        renderingQueue.sortWith { o1, o2 -> o1[PositionComponent.mapper]!!.z.compareTo(o2[PositionComponent.mapper]!!.z) }

        this.batch.begin()

        for (entity in renderingQueue) {
            val bodyComponent = entity[MachineComponent.mapper]!!

            val regionName =
                machAssetsManager.machineTileName(bodyComponent.kind.machineName, bodyComponent.level)

            val bodyCenter = bodyComponent.body.position

            for (f in bodyComponent.body.fixtureList) {
                val transform: Transform = f.body.transform

                when (f.userData) {
                    MachinePart.BODY -> {
                        val bodySprite =
                            Sprite(MachAssetsManager.textureFromAtlasRegion(machAssetsManager.machineBodies, regionName))
                        bodySprite.setOriginCenter()
                        bodySprite.setPosition(bodyCenter.x * PPM, bodyCenter.y * PPM)
                        bodySprite.draw(this.batch)
                    }
                    MachinePart.LEFT_WEAPON -> {
//                        val weaponSprite =
//                            Sprite(MachAssetsManager.textureFromAtlasRegion(machAssetsManager.machineWeapons, regionName))
//                        weaponSprite.setOriginCenter()
//
//                        var pos: Vector2 = vec2(1f, 1f)
//                        (f.shape as PolygonShape).getVertex(0, pos)
//                        pos = transform.mul(pos)
//
//                        weaponSprite.setPosition(pos.x * PPM, pos.y * PPM)
//                        weaponSprite.draw(this.batch)
                    }
                    MachinePart.RIGHT_WEAPON -> {
//                        val weaponSprite =
//                            Sprite(MachAssetsManager.textureFromAtlasRegion(machAssetsManager.machineWeapons, regionName))
//                        weaponSprite.setOriginCenter()
//
//                        var pos: Vector2 = vec2(1f, 1f)
//                        (f.shape as PolygonShape).getVertex(0, pos)
//                        pos = transform.mul(pos)
//
//                        weaponSprite.setPosition(pos.x * PPM, pos.y * PPM)
//                        weaponSprite.draw(this.batch)
                    }
                }
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