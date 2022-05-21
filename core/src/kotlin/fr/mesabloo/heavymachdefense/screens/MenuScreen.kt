package fr.mesabloo.heavymachdefense.screens

import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.PositionComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.managers.assets.MenuAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import ktx.ashley.entity
import ktx.ashley.with

class MenuScreen(game: MainGame, isLoading: Boolean = false) : AbstractScreen(game, isLoading) {
    override fun show() {
        super.show()

        if (this.isLoading)
            return

        // NOTE: this should have already been added by the loading component
        this.ui.engine.addSystem(RenderPositionedTextures(this.ui.batch))

        this.ui.engine.entity {
            with<TextureComponent> {
                texture = menuAssetsManager.get(MenuAssetsManager.BACKGROUND)
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                z = 0f
            }
        }
        this.ui.engine.entity {
            with<TextureComponent> {
                texture = menuAssetsManager.get(MenuAssetsManager.FOREGROUND)
            }
            with<PositionComponent> {
                x = 0f
                y = 0f
                z = 5f
            }
        }
    }

    override fun dispose() {
        super.dispose()

        menuAssetsManager.dispose()
    }
}