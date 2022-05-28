package fr.mesabloo.heavymachdefense.screens

import aurelienribon.tweenengine.Timeline
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenCallback.COMPLETE
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Quart
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
import fr.mesabloo.heavymachdefense.DEBUG
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.ui.debug.FPSDebugger
import fr.mesabloo.heavymachdefense.ifDebug
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.POSITION
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.SCALE
import fr.mesabloo.heavymachdefense.ui.loading.*
import fr.mesabloo.heavymachdefense.world.UIWorld
import fr.mesabloo.heavymachdefense.world.UI_HEIGHT
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.app.KtxScreen
import kotlin.properties.Delegates

abstract class AbstractScreen(val game: MainGame, isLoading: Boolean = false) : KtxScreen {
    val ui = UIWorld()
    val background = Group()
    private val foreground = Group()

    private lateinit var fpsDebugger: FPSDebugger

    init {
        this.ui.addActor(this.background)
        this.ui.addActor(this.foreground)

        if (DEBUG) {
            this.foreground.addActor(FPSDebugger().also {
                this.fpsDebugger = it

                it.setPosition(5f, 5f)
                it.touchable = Touchable.disabled
            })
        }
    }

    private var loadingAnimationDone: Boolean by Delegates.observable(false) { _, _, new ->
        if (new && loadingDone) {
            this.game.nextScreen()
        }
    }
    private var loadingDone: Boolean by Delegates.observable(false) { _, _, new ->
        if (new && loadingAnimationDone) {
            this.game.nextScreen()
        }
    }
    private lateinit var nextScreen: MainGame.() -> Unit

    protected val mux = InputMultiplexer()

    private val tweenManager = TweenManager()

    private companion object {
        const val LOADING_DURATION = 0.65f
        const val LOADING_END_DELAY = 0.5f

        private val easeEquation = Quart.INOUT
    }

    var isLoading: Boolean = isLoading
        private set

    init {
        Tween.registerAccessor(Actor::class.java, ActorAccessor())
    }

    override fun hide() {
        Gdx.app.debug(this.javaClass.simpleName, "Hiding application")
    }

    override fun show() {
        Gdx.app.debug(this.javaClass.simpleName, "Showing application")

        if (this.isLoading)
            return

        this.mux.addProcessor(this.ui)
    }

    override fun pause() {
        Gdx.app.debug(this.javaClass.simpleName, "Pausing application")

        Gdx.input.inputProcessor = null
    }

    override fun resume() {
        Gdx.app.debug(this.javaClass.simpleName, "Resuming application")

        Gdx.input.inputProcessor = this.mux
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

        ifDebug {
            this.fpsDebugger.zIndex = 50000
        }

        this.ui.act(delta)
        this.tweenManager.update(delta)
        this.ui.draw()
    }

    fun addLoadingOverlay(finishedLoading: () -> Boolean, switchScreen: MainGame.() -> Unit) {
        this.isLoading = true

        this.nextScreen = switchScreen

        Tween.call { _, source ->
            if (finishedLoading()) {
                this.loadingDone = true
                source.kill()
            }
        }
            .repeat(-1, 0.1f)
            .start(this.tweenManager)

        val tweens = mutableListOf<Tween>()

        this.foreground.addActor(LeftPane().also {
            it.setPosition(-it.width, 0f)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(0f, 0f)
                    .ease(easeEquation)
            )
        })
        this.foreground.addActor(RightPane().also {
            it.setPosition(UI_WIDTH, 0f)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(UI_WIDTH - it.width, 0f)
                    .ease(easeEquation)
            )
        })
        this.foreground.addActor(TopPane().also {
            it.setPosition(0f, UI_HEIGHT + it.height)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(0f, UI_HEIGHT - it.height)
                    .ease(easeEquation)
            )
        })
        this.foreground.addActor(BottomPane().also {
            it.setPosition(0f, -2f * it.height)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(0f, 0f)
                    .ease(easeEquation)
            )
        })
        this.foreground.addActor(CenterDot().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f, -1158f)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(UI_WIDTH / 2f - it.width / 2f, UI_HEIGHT / 2f - it.height / 2.5f + 2f)
                    .ease(easeEquation)
            )
        })

        tweens.fold(Timeline.createParallel()) { timeline, tween -> timeline.push(tween) }
            .setCallbackTriggers(COMPLETE)
            .setCallback { _, _ -> this.loadingAnimationDone = true }
            .start(this.tweenManager)
    }

    fun addLoadingOverlayEnd() {
        this.isLoading = true

        val tweens = mutableListOf<Tween>()

        this.foreground.addActor(LeftPane().also {
            it.setPosition(0f, 0f)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(-it.width, 0f)
                    .ease(easeEquation)
                    .delay(LOADING_END_DELAY)
            )
        })
        this.foreground.addActor(RightPane().also {
            it.setPosition(UI_WIDTH - it.width, 0f)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(UI_WIDTH, 0f)
                    .ease(easeEquation)
                    .delay(LOADING_END_DELAY)
            )
        })
        this.foreground.addActor(TopPane().also {
            it.setPosition(0f, UI_HEIGHT - it.height)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(0f, UI_HEIGHT + it.height)
                    .ease(easeEquation)
                    .delay(LOADING_END_DELAY)
            )
        })
        this.foreground.addActor(BottomPane().also {
            it.setPosition(0f, 0f)

            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(0f, -2f * it.height)
                    .ease(easeEquation)
                    .delay(LOADING_END_DELAY)
            )
        })
        this.foreground.addActor(CenterDot().also {
            it.setPosition(UI_WIDTH / 2f - it.width / 2f, UI_HEIGHT / 2f - it.height / 2.5f + 2f)

            tweens.add(Tween.to(it, SCALE, 0.25f)
                .target(0.75f, 0.75f)
                .ease(easeEquation)
                .setCallbackTriggers(COMPLETE)
                .setCallback { _, _ ->
                    this.isLoading = false
                    this.show()
                })
            tweens.add(
                Tween.to(it, POSITION, LOADING_DURATION)
                    .target(UI_WIDTH / 2f - it.width / 2f, -1158f)
                    .ease(easeEquation)
                    .delay(LOADING_END_DELAY)
            )
        })

        tweens.fold(Timeline.createParallel()) { timeline, tween -> timeline.push(tween) }
            .setCallbackTriggers(COMPLETE)
            .setCallback { _, _ -> this@AbstractScreen.removeLoadingOverlayEnd() }
            .start(this.tweenManager)
    }

    fun removeLoadingOverlayEnd() {
        Gdx.app.debug(this.javaClass.simpleName, "Removing loading screen actors")

        this.foreground.children
            .filter { it is BottomPane || it is CenterDot || it is LeftPane || it is RightPane || it is TopPane }
            .forEach { it.remove() }

        Gdx.input.inputProcessor = this.mux
    }
}