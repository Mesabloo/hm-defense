package io.github.mesabloo.hmdefense.meta

import scala.compiletime.erasedValue

/** An empty trait to force type unification in various places, such as in the
  * [[If]] type or in the definition of [[debugSwitch_]].
  *
  * @tparam A
  */
trait Is[A]

/** A conditional type.
  *
  * @tparam Cond
  * @tparam T
  * @tparam U
  */
type If[Cond <: Boolean, T, U] = Is[Cond] match
  case Is[true]  => T
  case Is[false] => U

/** An erased conditional expression whose condition is at the type-level.
  *
  * @tparam Cond
  *   The type-level condition which dictates which parameter to return.
  * @tparam T
  *   The type of the `then` action.
  * @tparam U
  *   The type of the `else` action.
  *
  * @param `then`
  *   The value to return, may the condition be true.
  * @param `else`
  *   The value to return, may the condition be false.
  * @return
  *   Either `then` or `else` based on the type-value of `Cond`.
  */
transparent inline def erasedIf[Cond <: Boolean, T, U](
    inline `then`: => T,
    inline `else`: => U
): If[Cond, T, U] =
  inline erasedValue[Is[Cond]] match
    case _: Is[true]  => `then`
    case _: Is[false] => `else`
