package fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.managers.assets.MachAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.machAssetsManager
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

        val renderingQueue = this.renderingQueue.toMutableList().sortedBy { e -> e[PositionComponent.mapper]!!.z }

        this.batch.begin()

        for (entity in renderingQueue) {
            val bodyComponent = entity[MachineComponent.mapper]!!

            val regionName =
                machAssetsManager.machineTileName(bodyComponent.kind.machineName, bodyComponent.level)

            run { // main body
                val bodyCenter = bodyComponent.mainBody.position

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
            run { // left weapon
                val bodyCenter = bodyComponent.lWeaponBody.position

                val weaponSprite =
                    Sprite(
                        MachAssetsManager.textureFromAtlasRegion(
                            machAssetsManager.machineWeapons,
                            regionName
                        )
                    )
                weaponSprite.setOriginCenter()
                weaponSprite.setPosition(
                    bodyCenter.x * PPM - weaponSprite.width / 2,
                    bodyCenter.y * PPM - weaponSprite.height / 2
                )
                weaponSprite.draw(this.batch)
            }
            run { // right weapon
                val bodyCenter = bodyComponent.rWeaponBody.position

                val weaponSprite =
                    Sprite(
                        MachAssetsManager.textureFromAtlasRegion(
                            machAssetsManager.machineWeapons,
                            regionName
                        )
                    )
                weaponSprite.setOriginCenter()
                weaponSprite.flip(false, true)
                weaponSprite.setPosition(
                    bodyCenter.x * PPM - weaponSprite.width / 2,
                    bodyCenter.y * PPM - weaponSprite.height / 2
                )
                weaponSprite.draw(this.batch)
            }
        }

        this.batch.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        this.renderingQueue.add(entity)
    }
}