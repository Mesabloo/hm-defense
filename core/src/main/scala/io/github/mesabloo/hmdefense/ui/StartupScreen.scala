package io.github.mesabloo.hmdefense.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import io.github.mesabloo.hmdefense.app.GameEntryPoint
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.textures.{StartupTextures, given}
import io.github.mesabloo.hmdefense.config.whenDebug
import io.github.mesabloo.hmdefense.ui.stage.{MainStage, VP_HEIGHT, VP_WIDTH}
import io.github.mesabloo.hmdefense.ui.widgets.FlashingImage

import scala.compiletime.uninitialized

/** The starting screen, which flashes the `Tap to start game` text in the
  * middle.
  *
  * @param game
  *   An instance of the global game.
  */
final class StartupScreen(private val game: GameEntryPoint)
    extends BaseScreen(game, false) {
  override protected val requiredTextures: Array[String] = Array(
    StartupTextures.Background,
    StartupTextures.TapToStart
  )

  override protected val requiredFonts: Array[String] = Array()

  override protected val requiredAtlases: Array[String] = Array()

  override protected val requiredAdditionalAssets: Map[String, Class[?]] = Map()

  /** The background startup image.
    */
  private var background: Image = uninitialized

  /** The `Tap to start` image at the very center of the screen.
    */
  private var tapToStart: FlashingImage = uninitialized

  protected override def onFinishLoading(): Unit =
    whenDebug:
      assert(this.requiredTextures.forall(assetManager.isLoaded))

    // - Load and position the background image.
    this.background = Image(
      assetManager.get[Texture](StartupTextures.Background)
    )
    this.background.setSize(VP_WIDTH, VP_HEIGHT)
    this.background.setPosition(0f, 0f)
    this.background.setTouchable(Touchable.enabled)

    // - Load and position the `Tap to start` image.
    this.tapToStart = FlashingImage(
      assetManager.get[Texture](StartupTextures.TapToStart),
      1f
    )
    this.tapToStart.setPosition(
      VP_WIDTH / 2f - this.tapToStart.getPrefWidth / 2f,
      VP_HEIGHT / 2f - this.tapToStart.getPrefHeight / 2f
    )
    this.tapToStart.setTouchable(Touchable.disabled)

    // - Put all widgets on the stage.
    MainStage.addActor(this.background)
    MainStage.addActor(this.tapToStart)
}
