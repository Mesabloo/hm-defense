package fr.mesabloo.heavymachdefense.entities

import com.badlogic.gdx.physics.box2d.BodyDef
import fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.data.UpgradeKind
import fr.mesabloo.heavymachdefense.data.Upgrades
import fr.mesabloo.heavymachdefense.ui.stage.game.AllyBase
import fr.mesabloo.heavymachdefense.ui.stage.game.EnemyBase
import fr.mesabloo.heavymachdefense.world.GameWorld
import ktx.box2d.body
import ktx.box2d.box
import kotlin.reflect.KProperty0

fun createBases(world: GameWorld, upgrades: Upgrades, save: KProperty0<GameSave>): Pair<AllyBase, EnemyBase> {
    val allyBase = AllyBase(save.get().mainUpgrades[UpgradeKind.BASE_DEFENSE] ?: 1, save.get().mainUpgrades[UpgradeKind.BASE_CANNON] ?: 1)
    val enemyBase = EnemyBase()

    world.world.body {
        type = BodyDef.BodyType.StaticBody
        box(width = 256f / PPM, height = 128f / PPM) {
            density = 10000000f
            isSensor = true
        }
        userData = allyBase

        position.set(512f / 2f / PPM, (128f / 2f + 16f) / PPM)
    }
    world.world.body {
        type = BodyDef.BodyType.StaticBody
        box(width = 256f / PPM, height = 128f / PPM) {
            density = 10000000f
            isSensor = true
        }
        userData = enemyBase

        position.set(512f / 2f / PPM, (2048f - 128f / 2f + 8f) / PPM)
    }

    return Pair(allyBase, enemyBase)
}