package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.TextureRegion
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.AssetsManager
import ktx.ashley.entity
import ktx.ashley.with

fun createBackground(engine: Engine, lvl: Int): Collection<Entity> = listOf(
    engine.entity {
        with<TextureComponent> {
            texture = TextureRegion(AssetsManager.background(lvl, 1))
        }
        with<PositionComponent> {
            x = 0f
            y = 0f
            z = 0f
        }
    },
    engine.entity {
        with<TextureComponent> {
            texture = TextureRegion(AssetsManager.background(lvl, 2))
        }
        with<PositionComponent> {
            x = 0f
            y = 1024f
            z = 0f
        }
    }
)