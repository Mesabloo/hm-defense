package fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import fr.mesabloo.heavymachdefense.BG_BORDER
import fr.mesabloo.heavymachdefense.PPM
import fr.mesabloo.heavymachdefense.components.BodyComponent
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.managers.assets.backgroundAssetsManager
import fr.mesabloo.heavymachdefense.world.WORLD_HEIGHT
import fr.mesabloo.heavymachdefense.world.WORLD_WIDTH
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box

fun createBackground(engine: Engine, lvl: Int, world: World): Collection<Entity> = listOf(
    engine.entity {
        with<TextureComponent> {
            texture = TextureRegion(backgroundAssetsManager.texture(lvl, 1))
            width = 512f
            height = 1024f
        }
        with<PositionComponent> {
            x = 0f
            y = 0f
            z = 0f
        }
    },
    engine.entity {
        with<TextureComponent> {
            texture = TextureRegion(backgroundAssetsManager.texture(lvl, 2))
            width = 512f
            height = 1024f
        }
        with<PositionComponent> {
            x = 0f
            y = 1024f
            z = 0f
        }
    },
    engine.entity {
        with<BodyComponent> {
            this.body = world.body {
                val width = WORLD_WIDTH / PPM
                val height = 2 * WORLD_HEIGHT / PPM

                type = BodyDef.BodyType.StaticBody
                box(width, height) {
                    restitution = 0f
                    density = 100f
                    isSensor = false
                }
                position.set(width / 2f, height / 2f)
                userData = BG_BORDER
                awake = true
            }
        }
    }
)