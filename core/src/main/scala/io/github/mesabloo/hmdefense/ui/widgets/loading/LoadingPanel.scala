package io.github.mesabloo.hmdefense.ui.widgets.loading

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import io.github.mesabloo.hmdefense.animations.{Animation, given}
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.textures.{CommonTextures, given}
import io.github.mesabloo.hmdefense.config.whenDebug
import io.github.mesabloo.hmdefense.ui.stage.{VP_HEIGHT, VP_WIDTH}

enum SlidingDirection derives CanEqual:
  case Left
  case Right
  case Down
  case Up

final class LoadingPanel(
    slidingDirection: SlidingDirection,
    fromX: Float,
    fromY: Float,
    toX: Float,
    toY: Float
) extends Group:
  whenDebug:
    assert(slidingDirection match
      case SlidingDirection.Left | SlidingDirection.Right => fromY == toY
      case SlidingDirection.Down | SlidingDirection.Up    => fromX == toX
    )

//  private given tupleFloatFloatAnimated: Animated[(Float, Float)] =
//    new Animated[(Float, Float)]:
//      override def withProgress[U: Numeric](
//          progress: U,
//          to: (Float, Float),
//          from: (Float, Float)
//      ): (Float, Float) =
//        (
//          progress.asInstanceOf[Float] * (to._1 - from._1),
//          progress.asInstanceOf[Float] * (to._2 - from._2)
//        )
//
//      override def step(
//          init: (Float, Float),
//          step: (Float, Float)
//      ): (Float, Float) = (init._1 + step._1, init._2 + step._2)

  @noinline
  private val animation = Animation[(Float, Float)](
    Interpolation.circleIn,
    // Please keep those unnecessary parentheses, otherwise scalafmt breaks.
    (slidingDirection match
      case SlidingDirection.Left | SlidingDirection.Right => 0.4f
      case SlidingDirection.Down | SlidingDirection.Up    => 0.4f
    ),
    (fromX, fromY),
    (toX, toY),
    (posX, posY) => this.setPosition(posX, posY),
    anim =>
      // Reverse the animation if we were going in reverse
      if this.gniog then anim.reverse()
      this.going = false
      this.gniog = false
  )

  /** A boolean flag indicating whether the animation is currently playing. It
    * must be `false` if [[Animation.isFinished]] is `true`.
    */
  private var going = false

  /** A boolean flag indicating whether the animation is playing in reverse. As
    * for [[Animation.going]], it must be `false` whenever
    * [[Animation.isFinished]] is `true`.
    */
  private var gniog = false

  /** Starts up the animation from 0. It has an effect only when the current
    * animation is not playing.
    */
  def go(): Unit =
    if !going then
      animation.restart()
      going = true

  /** Starts up the animation from 0, but in reverse. It has an effect only when
    * the current animation is not already playing in reverse.
    */
  def og(): Unit =
    if !gniog then
      animation.reverse()
      animation.restart()
      gniog = true

  override def act(delta: Float): Unit =
    if going || gniog then animation.update(delta)

    super.act(delta)

  private val innerImage = Image(
    assetManager.get[Texture](slidingDirection match
      case SlidingDirection.Left  => CommonTextures.LoadingRight
      case SlidingDirection.Right => CommonTextures.LoadingLeft
      case SlidingDirection.Down  => CommonTextures.LoadingTop
      case SlidingDirection.Up    => CommonTextures.LoadingBottom
    )
  )

  this.setSize(VP_WIDTH, VP_HEIGHT)

  this.addActor(innerImage)
  slidingDirection match
    case SlidingDirection.Left =>
      innerImage.setPosition(this.getWidth - innerImage.getWidth, 0)
    case SlidingDirection.Right => innerImage.setPosition(0, 0)
    case SlidingDirection.Down =>
      innerImage.setPosition(0, this.getHeight - innerImage.getHeight)
    case SlidingDirection.Up =>
      innerImage.setPosition(0, 0)
