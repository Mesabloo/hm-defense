package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Transform
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.MachinePart
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.MachAssetsManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.machAssetsManager
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.math.times
import ktx.math.vec2

class RenderMachinesSystem(private val batch: SpriteBatch) : IteratingSystem(
    allOf(
        MachineComponent::class
    ).get()
) {
    private var renderingQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val renderingQueue = this.renderingQueue.toMutableList().sortedBy { e -> e[PositionComponent.mapper]!!.z }

        this.batch.begin()

        for (entity in renderingQueue) {
            val bodyComponent = entity[MachineComponent.mapper]!!

            val regionName =
                machAssetsManager.machineTileName(bodyComponent.kind.machineName, bodyComponent.level)

            val bodyCenter = bodyComponent.body.position

            val fixtures = bodyComponent.body.fixtureList.sortedBy { f -> f.userData as MachinePart }

            for (f in fixtures) {
                val transform: Transform = f.body.transform

                when (f.userData) {
                    MachinePart.BODY -> {
                        val bodySprite =
                            Sprite(
                                MachAssetsManager.textureFromAtlasRegion(
                                    machAssetsManager.machineBodies,
                                    regionName
                                )
                            )
                        bodySprite.setOriginCenter()
                        bodySprite.setPosition(
                            bodyCenter.x * PPM - bodySprite.width / 2,
                            bodyCenter.y * PPM - bodySprite.height / 2
                        )
                        bodySprite.draw(this.batch)
                    }
                    MachinePart.LEFT_WEAPON -> {
                        val weaponSprite =
                            Sprite(
                                MachAssetsManager.textureFromAtlasRegion(
                                    machAssetsManager.machineWeapons,
                                    regionName
                                )
                            )
                        weaponSprite.setOriginCenter()

                        var pos: Vector2 = vec2(1f, 1f)
                        (f.shape as PolygonShape).getVertex(0, pos)
                        pos = transform.mul(pos) * PPM

                        weaponSprite.setPosition(pos.x, pos.y)
                        weaponSprite.draw(this.batch)
                    }
                    MachinePart.RIGHT_WEAPON -> {
                        val weaponSprite =
                            Sprite(
                                MachAssetsManager.textureFromAtlasRegion(
                                    machAssetsManager.machineWeapons,
                                    regionName
                                )
                            )
                        weaponSprite.setOriginCenter()
                        weaponSprite.flip(false, true)

                        var pos: Vector2 = vec2(1f, 1f)
                        (f.shape as PolygonShape).getVertex(0, pos)
                        pos = transform.mul(pos) * PPM

                        weaponSprite.setPosition(pos.x, pos.y)
                        weaponSprite.draw(this.batch)
                    }
                    MachinePart.LEFT_FOOT -> {

                    }
                    MachinePart.RIGHT_FOOT -> {

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