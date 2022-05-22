package fr.mesabloo.heavymachdefense.systems.rendering

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.components.machine.MachineComponent
import fr.mesabloo.heavymachdefense.internal.Batcher
import fr.mesabloo.heavymachdefense.managers.assets.MachAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.machAssetsManager
import ktx.ashley.allOf
import ktx.ashley.get

class RenderMachinesSystem(private val batcher: Batcher) : IteratingSystem(
    allOf(
        MachineComponent::class
    ).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
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
            this.batcher.draw(bodySprite, 500)
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
            this.batcher.draw(weaponSprite, 505)
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
            this.batcher.draw(weaponSprite, 505)
        }
    }
}