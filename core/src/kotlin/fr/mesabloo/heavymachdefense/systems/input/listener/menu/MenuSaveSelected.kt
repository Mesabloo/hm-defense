package fr.mesabloo.heavymachdefense.systems.input.listener.menu

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.components.saves.SaveComponent
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import fr.mesabloo.heavymachdefense.screens.MenuSelectionScreen
import ktx.ashley.allOf
import ktx.ashley.get

class MenuSaveSelected(private val game: MainGame, private val screen: MenuScreen, private val engine: Engine, private val entity: Entity, private val index: Int) :
        () -> Unit {
    @Suppress("NAME_SHADOWING")
    override fun invoke() {
        val textureComponent = entity[TextureComponent.mapper]!!
        val saveComponent = entity[SaveComponent.mapper]!!

        if (saveComponent.focused) {
            Gdx.input.inputProcessor = null

            levelSelectionAssetsManager.preload()
            buttonAssetsManager.preload()

            // NOTE: we know for sure that this is this screen as this system is instantiated only there
            val screen = this.game.`access$currentScreen` as MenuScreen
            screen.addLoadingOverlay({
                if (!assetManager.isFinished)
                    assetManager.update()
                levelSelectionAssetsManager.isFullyLoaded() && buttonAssetsManager.isFullyLoaded()
            }) {
                (this.changeScreen(lazy { MenuSelectionScreen(this, saveComponent.save, true) }) as AbstractScreen?)
                    ?.addLoadingOverlayEnd()

                this.getScreen<MenuScreen>().dispose()
                this.removeScreen<MenuScreen>()
            }
        } else {
            val allSaveEntities =
                this.engine.getEntitiesFor(allOf(SaveComponent::class, MouseInputComponent::class).get())
                    .filter { it[SaveComponent.mapper]!!.save != saveComponent.save }

            saveComponent.focused = true
            textureComponent.texture = menuAssetsManager.getSlot(true)
            screen.focusedIndex = index

            for (entity in allSaveEntities) {
                val saveComponent = entity[SaveComponent.mapper]!!
                saveComponent.focused = false

                val textureComponent = entity[TextureComponent.mapper]!!
                textureComponent.texture = menuAssetsManager.getSlot(false)
            }
        }
    }
}