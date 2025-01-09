package io.github.mesabloo.hmdefense.ui.widgets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import io.github.mesabloo.hmdefense.animations.{Animation, Instant, given}

/** An implementation of an [[Image]] flashing on screen every given delay in
  * seconds.
  *
  * @param tex
  *   The [[Texture]] of the image.
  * @param delay
  *   The delay of half of a flash (a single transition between two states).
  *   There are two states in a flash:
  *
  *   - From when the texture appeared to when the texture disappeared;
  *   - From when the texture disappeared to when the texture appeared.
  */
class FlashingImage(private val tex: Texture, private val delay: Float)
    extends Image(tex):
//  private implicit def animatedFloat: Animated[Float] =
//    new Animated[Float]:
//      override def withProgress[U: Fractional](
//          percent: U,
//          to: Float,
//          from: Float
//      ): Float = percent.asInstanceOf[Float] * (to - from)
//
//      override def step(init: Float, step: Float): Float = init + step

  private val flash = Animation(
    Instant,
    1f,
    0f,
    1f,
    alpha => this.getColor.a = alpha,
    anim =>
      anim.reverse()
      anim.restart()
  )

//  /** This is the time (in seconds) since the end of the last transition between
//    * opaque and translucent states.
//    */
//  private var timeSinceLastFlash = 0f

  override def act(delta: Float): Unit =
    this.flash.update(delta)
//    this.timeSinceLastFlash += delta
//    if this.timeSinceLastFlash >= this.delay
//    then
//      // Switch from invisible to visible, and from visible to invisible.
//      this.timeSinceLastFlash = 0f
//      this.getColor.a = 1f - this.getColor.a

    super.act(delta)
