package io.github.mesabloo.hmdefense.ui

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{BitmapFont, TextureAtlas}
import io.github.mesabloo.hmdefense.app.GameEntryPoint
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.textures.{CommonTextures, given}
import io.github.mesabloo.hmdefense.config.whenDebug
import io.github.mesabloo.hmdefense.ui.stage.MainStage

import scala.collection.immutable

/** This class should be inherited for each new kind of screen which requires
  * loading textures, fonts, etc.
  *
  * @param game
  *   An instance of the global game, useful to switch screens and stuff.
  * @param withLoading
  *   Whether the loading animation is to be played at the creation of the
  *   screen or not.
  */
abstract class BaseScreen(
    private val game: GameEntryPoint,
    private val withLoading: Boolean = true
) extends ScreenAdapter {
  whenDebug:
    assert(assetManager.isLoaded(CommonTextures.LoadingTop))
    assert(assetManager.isLoaded(CommonTextures.LoadingBottom))
    assert(assetManager.isLoaded(CommonTextures.LoadingLeft))
    assert(assetManager.isLoaded(CommonTextures.LoadingRight))
    assert(assetManager.isLoaded(CommonTextures.LoadingCenter))

  /** The set of all required texture paths (from the `assets/` folder). These
    * will all be loaded when first creating the screen.
    */
  protected val requiredTextures: Array[String]

  /** The set of all required bitmap font paths (from the `assets/` folder).
    * These will all be loaded upon creating the screen, similarly to
    * [[requiredTextures]].
    */
  protected val requiredFonts: Array[String]

  /** The set of all required texture atlas paths (from the `assets/` folder).
    * These will all be loaded upon creating the screen.
    */
  protected val requiredAtlases: Array[String]

  /** Some additional assets that may be required when loading the screen.
    * These are mappings from the paths to their kind of assets to load.
    */
  protected val requiredAdditionalAssets: immutable.Map[String, Class[?]]

  /** A boolean flag indicating whether all required assets have finished
    * loading or not.
    */
  private final var finishedLoading = false

  /** Abstract function called whenever all required assets have finally been
    * loaded.
    *
    * This will most likely be used to initialize the UI and stuff which we can
    * only do once all textures are loaded.
    */
  protected def onFinishLoading(): Unit

  /////////////////////////////////////////////////

  /** Computes whether all required textures have been loaded or not.
    * @return
    *   `true` if all required textures are loaded in the [[assetManager]],
    *   `false` otherwise.
    */
  private final inline def assetsFinishedLoading: Boolean =
    this.requiredTextures.forall(assetManager.isLoaded) &&
      this.requiredFonts.forall(assetManager.isLoaded) &&
      this.requiredAtlases.forall(assetManager.isLoaded) &&
      this.requiredAdditionalAssets.forall((path, _cls) =>
        assetManager.isLoaded(path)
      )

  override def show(): Unit =
    // Load all required textures.
    this.requiredTextures.foreach: path =>
      assetManager.load(path, classOf[Texture])
    // Load all required fonts
    this.requiredFonts.foreach: path =>
      assetManager.load(path, classOf[BitmapFont])
    // Load all required atlases
    this.requiredAtlases.foreach: path =>
      assetManager.load(path, classOf[TextureAtlas])
    // Load all other misc assets
    this.requiredAdditionalAssets.foreachEntry: (path, cls) =>
      assetManager.load(path, cls)

    // Make sure that the stage is cleared no matter what.
    MainStage.clear()

  /** A boolean flag indicating whether the loading screen has been
    * shown/initialized yet.
    */
  private final var notLoadedYet = true

  override def render(delta: Float): Unit =
    super.render(delta)

    // Always try to load assets if possible
    assetManager.update()

    if withLoading && notLoadedYet then
      notLoadedYet = false
      MainStage.showLoadingScreen(withLoading)

    // If we are finished loading all resources, then the call to [[assetsFinishedLoading]] should never be performed.
    // This should be ensured by the short-circuiting semantics of the logical AND.
    if !this.finishedLoading && assetsFinishedLoading then
      // Try to hide the loading screen when we are finished loading resources
      if withLoading then MainStage.hideLoadingScreen()

      onFinishLoading()
      this.finishedLoading = true

    MainStage.act(delta)
    MainStage.draw()

  final override def resize(width: Int, height: Int): Unit =
    MainStage.getViewport.update(width, height, true)
    super.resize(width, height)

  override def dispose(): Unit =
    this.requiredTextures.foreach(assetManager.unload)
    this.requiredAtlases.foreach(assetManager.unload)
    this.requiredFonts.foreach(assetManager.unload)
    this.requiredAdditionalAssets.foreachEntry((path, _cls) =>
      assetManager.unload(path)
    )

    super.dispose()
}
