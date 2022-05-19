package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.screens

import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components.ui.TextComponent
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.systems.rendering.ui.RenderTextSystem
import fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.world.UIWorld
import ktx.ashley.entity
import ktx.ashley.with

class LoadingScreen: AbstractScreen() {
    private val ui = UIWorld()

    init {
        this.ui.engine.addSystem(RenderTextSystem(this.ui.batch))

        this.ui.engine.entity {
            with<TextComponent> {
                message = "69420"
                font = fontManager.fonts[FontManager.STAGE]!!
            }
            with<PositionComponent> {
                x = this@LoadingScreen.ui.camera.viewportWidth / 2
                y = this@LoadingScreen.ui.camera.viewportHeight / 2
                z = 0f
            }
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        this.ui.resize(width, height)
    }

    override fun render(delta: Float) {
        super.render(delta)
        this.ui.render(delta)
    }

    override fun dispose() {
        this.ui.dispose()

        super.dispose()
    }
}