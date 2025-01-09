/** Object and functions in this package correspond to the ones found in
  * [[https://easings.net/]].
  */
package io.github.mesabloo.hmdefense.animations

import com.badlogic.gdx.math.Interpolation
import io.github.mesabloo.hmdefense.config.whenDebug

import scala.math.{pow, sqrt}

/**
  */
//object Circ:
//  /**
//    * @param progress
//    *   The animation progress percentage, between 0 and 1 (from 0% to 100%).
//    * @return
//    *   The new progress percentage.
//    */
//  inline def easeIn(progress: Float): Float =
//    whenDebug:
//      assert(progress >= 0 && progress <= 1)
//    1 - sqrt(1 - pow(progress, 2)).toFloat

///** The [[https://easings.net/#easeInCirc circ ease in function]].
//  */
//object CircEaseIn extends Interpolation:
//  inline override def apply(alpha: Float): Float =
//    whenDebug:
//      assert(alpha >= 0 && alpha <= 1)
//    1 - sqrt(1 - pow(alpha, 2)).toFloat

/** Basically the
  * [[https://en.wikipedia.org/wiki/Heaviside_step_function Heaviside step function `H`]]
  * but centered on 1 instead of 0.
  */
object Instant extends Interpolation:
  inline override def apply(alpha: Float): Float =
    whenDebug:
      assert(alpha >= 0 && alpha <= 1)
    if alpha < 1 then 0 else 1

///**
//  */
//object Linear:
//  /** The linear function.
//    *
//    * @param progress
//    *   The animation progress percentage, between 0 and 1 (from 0% to 100%).
//    * @return
//    *   The new progress percentage.
//    */
//  inline def inout(progress: Float): Float =
//    whenDebug:
//      assert(progress >= 0 && progress <= 1)
//    Interpolation.linear.apply(progress)
