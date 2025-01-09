package io.github.mesabloo.hmdefense.animations

import com.badlogic.gdx.math.Interpolation
import io.github.mesabloo.hmdefense.config.whenDebug

/** A trait of types that can be animated within [[Animation]]s.
  *
  * @tparam T
  *   The type that can be animated.
  */
trait Animated[T: Ordering]:
  def withProgress[U: Fractional](progress: U, to: T, from: T): T
  def step(init: T, step: T): T

given Animated[Float] with
  override def withProgress[U: Fractional](
      progress: U,
      to: Float,
      from: Float
  ): Float = progress.asInstanceOf[Float] * (to - from)

  override def step(init: Float, step: Float): Float = init + step

given [T: Animated: Ordering, U: Animated: Ordering]: Animated[(T, U)] with
  override def withProgress[V: Fractional](
      progress: V,
      to: (T, U),
      from: (T, U)
  ): (T, U) = (
    summon[Animated[T]].withProgress(progress, to._1, from._1),
    summon[Animated[U]].withProgress(progress, to._2, from._2)
  )

  override def step(init: (T, U), step: (T, U)): (T, U) =
    (
      summon[Animated[T]].step(init._1, step._1),
      summon[Animated[U]].step(init._2, step._2)
    )

/** An animation, which may or not be running.
  *
  * @param easing
  *   The easing function to be used.
  * @param duration
  *   The duration of the animation in seconds.
  * @param from
  *   The initial parameter to start the animation from.
  * @param to
  *   The final value to attain in the given duration.
  * @param onUpdate
  *   What to do everytime we update the animation. This parameter is used to
  *   link the animation with any concrete
  *   [[com.badlogic.gdx.scenes.scene2d.Actor]] or other stuff manipulated.
  */
class Animation[T: Animated: Ordering](
    private val easing: Interpolation,
    private val duration: Float,
    private var from: T,
    private var to: T,
    private val onUpdate: T => Unit,
    private val onCompleted: Animation[T] => Unit
):
  // Just to make sure our object is correctly set.
  this.onUpdate(from)

  /** A simple variable to keep track of where we are at in the animation.
    */
  private var progress = from

  /** The elapsed time (in seconds) since the beginning of the animation.
    */
  private var elapsed: Float = 0f

  /** Whether the animation is finished or not.
    */
  private var completed = false

  /** Updates the animation to step by the given delta in seconds.
    * @param delta
    *   The delta of time in seconds that passed since the last call to
    *   [[update]].
    */
  def update(delta: Float): Unit =
    elapsed += delta
    if elapsed < duration then
      // Compute the percentage of time passed (between 0 and 1).
      val normalized = elapsed / duration
      whenDebug:
        assert(normalized >= 0 && normalized <= 1)

      // Then compute the value in the range `[from, to]` at easing percentage.
      progress = implicitly[Animated[T]].step(
        implicitly[Animated[T]].withProgress(
          this.easing(normalized),
          to,
          from
        ),
        from
      )

      this.onUpdate(progress)
    else if !completed then
      progress = to
      this.onUpdate(progress)
      completed = true
      this.onCompleted(this)
    else
      whenDebug:
        // The animation must have reached its destination by now, if all our computations are correct.
        assert(implicitly[Ordering[T]].compare(to, from) <= 0)

  /** Returns whether the animation has been fully completed.
    * @return
    *   `true` if the animation has been going on for long enough, `false`
    *   otherwise.
    */
  def isFinished: Boolean = this.completed

  /** Swaps the starting and ending points of the animation.
    */
  def reverse(): Unit =
    val tmp = from
    from = to
    to = tmp

  /** Start the animation over again, by setting its elapsed time to 0 seconds.
    */
  def restart(): Unit =
    this.elapsed = 0f
    this.completed = false
