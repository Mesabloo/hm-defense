package io.github.mesabloo.hmdefense.app

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch, TextureAtlas}
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.*
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.atlases.CommonAtlases
import io.github.mesabloo.hmdefense.assets.fonts.DebugFonts
import io.github.mesabloo.hmdefense.assets.textures.CommonTextures
import io.github.mesabloo.hmdefense.config.{DEBUG, whenDebug}
import io.github.mesabloo.hmdefense.logging.ColoredLogger
import io.github.mesabloo.hmdefense.ui.stage.MainStage
import io.github.mesabloo.hmdefense.ui.{BaseScreen, StartupScreen}

import java.util.Date

/** A template for HM defense games starting from a given initial
  * [[BaseScreen]].
  */
class GameEntryPoint(
    private final val initialScreen: GameEntryPoint => BaseScreen
) extends Game:
  /** A private field only used when debugging (if [[DEBUG]] is set to `true`)
    * to compute the time taken to load the application.
    */
  @noinline
  private final val startDate = Date()

  /** Returns the list of all common textures that are used throughout the game
    * (such as loading screen textures).
    * @return
    *   The list containing the paths to all commonly used textures.
    */
  private final inline def commonRequiredTextures: Array[String] =
    CommonTextures.values.map(_.path)

  /** Serves the same purpose as [[commonRequiredTextures]], but for
    * [[TextureAtlas]]es instead.
    * @return
    *   The list containing the paths to all commonly used texture atlases.
    */
  private final inline def commonRequiredAtlases: Array[String] =
    CommonAtlases.values.map(_.path)

  /** Same as [[commonRequiredTextures]] and [[commonRequiredAtlases]], but for
    * fonts.
    */
  private final inline def commonRequiredFonts: Array[String] =
    (inline if DEBUG then DebugFonts.values.map(_.path) else Array[String]())
      ++ Array[String]()

  /** A flag which is set to `true` only once [[allTexturesLoaded]] returns
    * `true`.
    */
  private final var ready = false

  final override def create(): Unit =
    Gdx.app.setLogLevel(
      if DEBUG then Application.LOG_DEBUG else Application.LOG_INFO
    )
    Gdx.app.setApplicationLogger(ColoredLogger)

    this.commonRequiredTextures.foreach: path =>
      assetManager.load(path, classOf[Texture])
    this.commonRequiredAtlases.foreach: path =>
      assetManager.load(path, classOf[TextureAtlas])
    this.commonRequiredFonts.foreach: path =>
      assetManager.load(path, classOf[BitmapFont])

  /** Computes whether all resources (textures, atlases, sounds, etc.) have been
    * successfully loaded into the asset manager or not.
    *
    * @return
    *   `true` if all required resources are loaded in the asset manager.
    *   `false` otherwise.
    */
  private final inline def allTexturesLoaded: Boolean =
    commonRequiredTextures.forall(assetManager.isLoaded) &&
      commonRequiredAtlases.forall(assetManager.isLoaded) &&
      commonRequiredFonts.forall(assetManager.isLoaded)

  final override def render(): Unit =
    ScreenUtils.clear(0, 0, 0, 1)

    // Do not change this condition: the goal is to prevent computing [[allTexturesLoaded]] when we already are [[ready]].
    // This is done by the short circuiting behavior of the logical OR, hence the condition.
    if ready || allTexturesLoaded then
      if !ready then
        whenDebug:
          Gdx.app.debug(
            this.getClass.getCanonicalName,
            s"Init took ${Date().getTime - this.startDate.getTime}ms"
          )

        ready = true

        // Initialize the main stage now that we are ready with everything
        MainStage.init()

        // Set the first screen
        this.changeScreen(this.initialScreen(this))
      else super.render()
    else assetManager.update()

  final override def dispose(): Unit =
    this.commonRequiredTextures.foreach(assetManager.unload)
    this.commonRequiredAtlases.foreach(assetManager.unload)
    this.commonRequiredFonts.foreach(assetManager.unload)

    super.dispose()

    assetManager.dispose()

  /** Change the currently shown screen. Calls [[Screen.hide]] on the current
    * screen (if any) and [[Screen.show]] on the new screen. Also calls
    * [[Screen.dispose]] on the old screen once it has been swapped.
    *
    * @param screen
    *   The new screen to replace the old one.
    * @return
    */
  final def changeScreen(screen: => Screen): Unit =
    val currentScreen = Option(this.getScreen)
    this.setScreen(screen)
    currentScreen.foreach(_.dispose())

/** This is the entry point of our application, starting from the
  * [[StartupScreen]].
  */
object HMMain extends GameEntryPoint(game => StartupScreen(game))
