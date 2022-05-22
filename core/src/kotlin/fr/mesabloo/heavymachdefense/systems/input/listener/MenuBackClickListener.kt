package fr.mesabloo.heavymachdefense.systems.input.listener

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.components.LoadingPartComponent
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.ui.OnClickListener
import fr.mesabloo.heavymachdefense.managers.assets.assetManager
import fr.mesabloo.heavymachdefense.managers.assets.welcomeAssetsManager
import fr.mesabloo.heavymachdefense.screens.AbstractScreen
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import fr.mesabloo.heavymachdefense.screens.WelcomeScreen
import ktx.ashley.exclude
import ktx.ashley.oneOf
import ktx.ashley.remove

class MenuBackClickListener(private val screen: MenuScreen, private val entity: Entity) : () -> Unit {
    override fun invoke() {
        Gdx.input.inputProcessor = null
        this.entity.remove<OnClickListener>()

        welcomeAssetsManager.preload()

        this.screen.addLoadingOverlay({
            if (!assetManager.isFinished)
                assetManager.update()
            welcomeAssetsManager.isFullyLoaded()
        }) {
            this@MenuBackClickListener.screen.ui.engine.removeAllEntities(
                oneOf(TextureComponent::class)
                    .exclude(LoadingPartComponent::class)
                    .get()
            )

            (this.changeScreen(lazy { WelcomeScreen(this, true) }) as AbstractScreen?)
                ?.addLoadingOverlayEnd()

            this.getScreen<MenuScreen>().dispose()
            this.removeScreen<MenuScreen>()
        }
    }
}