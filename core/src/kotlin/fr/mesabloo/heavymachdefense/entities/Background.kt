package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.TextureRegion
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.assets.backgroundAssetsManager
import ktx.ashley.entity
import ktx.ashley.with

fun createBackground(engine: Engine, lvl: Int): Collection<Entity> = listOf(
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
    }
)