package fr.mesabloo.heavymachdefense.systems.input.listener.stage_select

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.components.LoadingPartComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.ui.OnClickListener
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.buttonAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import fr.mesabloo.heavymachdefense.screens.MenuSelectionScreen
import ktx.ashley.exclude
import ktx.ashley.oneOf
import ktx.ashley.remove

class SelectBackClickListener(private val screen: MenuSelectionScreen, private val entity: Entity) : () -> Unit {
    override fun invoke() {
        Gdx.input.inputProcessor = null
        this.entity.remove<OnClickListener>()

        menuAssetsManager.preload()
        buttonAssetsManager.preload()

        this.screen.addLoadingOverlay({
            if (!assetManager.isFinished)
                assetManager.update()
            menuAssetsManager.isFullyLoaded() && buttonAssetsManager.isFullyLoaded()
        }) {
            this@SelectBackClickListener.screen.ui.engine.removeAllEntities(
                oneOf(TextureComponent::class)
                    .exclude(LoadingPartComponent::class)
                    .get()
            )

            (this.changeScreen(lazy { MenuScreen(this, true) }) as AbstractScreen?)
                ?.addLoadingOverlayEnd()

            this.getScreen<MenuSelectionScreen>().dispose()
            this.removeScreen<MenuSelectionScreen>()
        }
    }
}