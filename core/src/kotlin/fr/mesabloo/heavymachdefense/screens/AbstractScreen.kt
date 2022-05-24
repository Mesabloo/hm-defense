package fr.mesabloo.heavymachdefense.screens

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.components.LoadingPartComponent
import fr.mesabloo.heavymachdefense.entities.ui.createLoadingClose
import fr.mesabloo.heavymachdefense.entities.ui.createLoadingOpen
import fr.mesabloo.heavymachdefense.systems.LoadingFinishedSystem
import fr.mesabloo.heavymachdefense.systems.SignalWhenFinishedLoadingSystem
import fr.mesabloo.heavymachdefense.systems.rendering.RenderPositionedTextures
import fr.mesabloo.heavymachdefense.systems.rendering.animation.ScaleAnimationSystem
import fr.mesabloo.heavymachdefense.systems.rendering.animation.TransformAnimationSystem
import fr.mesabloo.heavymachdefense.world.UIWorld
import ktx.app.KtxScreen
import ktx.ashley.allOf

abstract class AbstractScreen(protected val game: MainGame, isLoading: Boolean = false) : KtxScreen {
    val ui = UIWorld()

    private val loadingDoneSignal: Signal<Unit> = Signal()
    private val loadingAnimationFinishedSignal: Signal<Unit> = Signal()
    private val loadingAnimationFinishedSignal2: Signal<Unit> = Signal()

    var isLoading: Boolean = isLoading
        private set

    abstract fun setupInputProcessor()

    override fun hide() {
        Gdx.app.debug(this.javaClass.simpleName, "Hiding application")
    }

    override fun show() {
        Gdx.app.debug(this.javaClass.simpleName, "Showing application")
    }

    override fun pause() {
        Gdx.app.debug(this.javaClass.simpleName, "Pausing application")
    }

    override fun resume() {
        Gdx.app.debug(this.javaClass.simpleName, "Resuming application")
    }

    override fun dispose() {
        Gdx.app.debug(this.javaClass.simpleName, "Disposing screen")
        super.dispose()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        this.ui.resize(width, height)
    }

    override fun render(delta: Float) {
        super.render(delta)

        this.ui.render(delta)
    }

    fun addLoadingOverlay(finishedLoading: () -> Boolean, switchScreen: MainGame.() -> Unit) {
        this.isLoading = true

        this.ui.engine.addSystem(TransformAnimationSystem(this.loadingAnimationFinishedSignal))
        this.ui.engine.addSystem(LoadingFinishedSystem(this.loadingAnimationFinishedSignal, this.loadingDoneSignal) {
            this.game.switchScreen()
        })
        this.ui.engine.addSystem(SignalWhenFinishedLoadingSystem(this.loadingDoneSignal, finishedLoading))
        this.ui.engine.addSystem(RenderPositionedTextures(this.ui.batch))

        createLoadingClose(this.ui.engine)
    }

    private lateinit var loadingOverlaySystems: List<EntitySystem>

    fun addLoadingOverlayEnd() {
        this.isLoading = true
        //this.loadingDoneSignal.add { _, _ -> this.game.switchScreen() }

        this.loadingOverlaySystems = listOf(
            TransformAnimationSystem(this.loadingAnimationFinishedSignal2),
            ScaleAnimationSystem(this.loadingAnimationFinishedSignal),
            RenderPositionedTextures(this.ui.batch)
        )
        for (system in this.loadingOverlaySystems) {
            this.ui.engine.addSystem(system)
        }

        this.loadingAnimationFinishedSignal.add { _, _ ->
            Gdx.app.debug(this.javaClass.simpleName, "Finished scaling animation")

            this.isLoading = false
            this.show()
        }
        this.loadingAnimationFinishedSignal2.add { _, _ ->
            Gdx.app.debug(this.javaClass.simpleName, "Removing entities")

            this@AbstractScreen.removeLoadingOverlayEnd()
            this.setupInputProcessor()
        }

        createLoadingOpen(this.ui.engine)
    }

    private fun removeLoadingOverlayEnd() {
        this.ui.engine.removeAllEntities(allOf(LoadingPartComponent::class).get())

        for (system in this.loadingOverlaySystems) {
            this.ui.engine.removeSystem(system)
        }
    }
}