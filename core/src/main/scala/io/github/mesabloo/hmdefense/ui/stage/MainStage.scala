package io.github.mesabloo.hmdefense.ui.stage

import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Table}
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Gdx, Input}
import io.github.mesabloo.hmdefense.animations.{Animation, Linear, given}
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.textures.{CommonTextures, given}
import io.github.mesabloo.hmdefense.config.{DEBUG, whenDebug}
import io.github.mesabloo.hmdefense.ui.widgets.DebugInfo
import io.github.mesabloo.hmdefense.ui.widgets.loading.{LoadingPanel, SlidingDirection}

import scala.compiletime.uninitialized

/** The width of the viewport.
  */
inline val VP_WIDTH = 768f

/** The height of the viewport.
  */
inline val VP_HEIGHT = 1024f

/** This is the [[TwoLayerStage]] that we will use throughout the whole game,
  * everywhere. Note that it is a singleton, as we will be hacking with it to
  * have a smooth loading animation without flickering at any point.
  */
object MainStage extends TwoLayerStage(DefaultLayer.Background):
  /// Set the viewport to always fit the screen.
  this.setViewport(
    FitViewport(VP_WIDTH, VP_HEIGHT, OrthographicCamera(VP_WIDTH, VP_HEIGHT))
  )
  /// Set the camera to look exactly at the center of the viewport.
  this.getCamera.position.set(VP_WIDTH / 2f, VP_HEIGHT / 2f, 0f)
  this.getCamera.update()

  // Setup an input processor for our stage.
  Gdx.input.setInputProcessor(this)

  /** A field holding the actor used to display debugging information (such as
    * FPS or CPU usage).
    */
  private final var debugInfo: Table = uninitialized

  final def init(): Unit =
    whenDebug:
      this.debugInfo = DebugInfo(30, 45, 40, 80, 40, 100)
      this.debugInfo.setPosition(0f, 0f, Align.bottomLeft)
      this.debugInfo.setDebug(false, false)

      this.foreground.addActor(this.debugInfo)

  final override def keyDown(keyCode: Int): Boolean =
    if DEBUG then
      keyCode match
        // When C-D is pressed, toggle debugging actor edges and stuff
        case Input.Keys.D if Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) =>
          this.setDebugAll(!this.isDebugAll)
          this.debugInfo.setDebug(false, false)
          true
        // When C-f is pressed while in debug mode, toggle visibility of debugging info
        case Input.Keys.F if Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) =>
          this.debugInfo.setVisible(!this.debugInfo.isVisible)
          true
        case _ => super.keyDown(keyCode)
    else super.keyDown(keyCode)

  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean =
    if DEBUG then
      button match
        // When the middle mouse button is pressed along with control, while in debug mode,
        // screen coordinates of the click will be output to the console.
        case Input.Buttons.MIDDLE
            if Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && this
              .isInsideViewport(screenX, screenY) =>
          val coords =
            this.getViewport.getCamera
              .unproject(Vector3(screenX.toFloat, screenY.toFloat, 0))
          Gdx.app.log(
            getClass.getCanonicalName,
            s"Screen touched at $coords"
          )
          true
        case _ => super.touchDown(screenX, screenY, pointer, button)
    else super.touchDown(screenX, screenY, pointer, button)

  // ******* LOGIC FOR LOADING SCREENS ********

  private final var panelInitialized = false
  private final var upPanel: LoadingPanel = uninitialized
  private final var downPanel: LoadingPanel = uninitialized
  private final var centerPanel: Image = uninitialized
  private final var leftPanel: LoadingPanel = uninitialized
  private final var rightPanel: LoadingPanel = uninitialized

  /** Tries to make the loading panels come back into view.
    *
    * @param shouldShowScreen
    *   Controls whether the loading is to be shown at its final place, or not.
    *   If `false`, the variables holding the loading screen panels will only be
    *   set. If `true` and the loading screen has never been shown before, the
    *   variables holding the loading screen will be set, and the loading screen
    *   will be shown without animation.
    */
  final def showLoadingScreen(shouldShowScreen: Boolean = true): Unit =
    Gdx.app.log(getClass.getCanonicalName, "Showing loading screen")
    // TODO: disallow any interaction (keyboard/touch) with the scene until the loading screen is off

    if !panelInitialized then
      this.upPanel = LoadingPanel(SlidingDirection.Down, 0f, VP_HEIGHT, 0f, 0f)
      this.downPanel = LoadingPanel(SlidingDirection.Up, 0f, -VP_HEIGHT, 0f, 0f)
      this.leftPanel =
        LoadingPanel(SlidingDirection.Right, -VP_WIDTH, 0f, 0f, 0f)
      this.rightPanel =
        LoadingPanel(SlidingDirection.Left, VP_WIDTH, 0f, 0f, 0f)
      this.centerPanel = Image(
        assetManager.get[Texture](CommonTextures.LoadingCenter)
      )
      this.centerPanel.setOrigin(Align.center)

      if shouldShowScreen then
        this.upPanel.setPosition(0f, 0f)
        this.downPanel.setPosition(0f, 0f)
        this.leftPanel.setPosition(0f, 0f)
        this.rightPanel.setPosition(0f, 0f)

      this.foreground.addActor(this.leftPanel)
      this.foreground.addActor(this.rightPanel)
      this.foreground.addActor(this.upPanel)
      this.foreground.addActor(this.downPanel)
      this.foreground.addActor(this.centerPanel)

      this.panelInitialized = true

    this.upPanel.go()
    this.downPanel.go()
    this.leftPanel.go()
    this.rightPanel.go()

  /** The main animation for the button press of [[MainStage.centerPanel]],
    * right after all resources have finished loading, just before the loading
    * screen disappears.
    */
  private var centerPressAnimation: Option[Animation[Float]] = Option.empty

  final def hideLoadingScreen(): Unit =
    Gdx.app.log(getClass.getCanonicalName, "Hiding loading screen")

    // Make animation for central button
    this.centerPressAnimation = Option(
      Animation(
        Linear.inout(_),
        0.2f, // 200ms
        1f,
        0.75f,
        x => this.centerPanel.setScale(x),
        anim => {
          this.upPanel.og()
          this.downPanel.og()
          this.leftPanel.og()
          this.rightPanel.og()

          this.centerPressAnimation = Option.empty
        }
      )
    )

  override def act(delta: Float): Unit =
    super.act(delta)

    // Set the position of the center loading button relative to the position of the panel going upwards.
    if panelInitialized then
      val x: Float = this.downPanel.getX(Align.top)
      val y: Float = this.downPanel.getY(Align.top)
      this.centerPanel.setPosition(x, y - VP_HEIGHT / 2f + 40f, Align.center)

      this.centerPressAnimation.foreach(_.update(delta))
