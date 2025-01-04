package io.github.mesabloo.hmdefense.config

import io.github.mesabloo.hmdefense.meta.erasedIf

/** A compile-time flag for whether to compile with debugging information or
  * not. Set to `true` if debugging information is wanted, otherwise `false`.
  */
inline val DEBUG = true

/** Performs one action or the other based on if debugging is enabled or not.
  *
  * @tparam T
  *   The return type of the debugging action.
  * @tparam U
  *   The return type of the non-debugging action.
  * @param f
  *   The action to be executed if debugging is enabled.
  * @param g
  *   The action to execute if debugging is disabled.
  * @return
  *   The value returned by the action executed.
  */
transparent inline def debugSwitch[T, U](inline f: => T)(inline g: => U) =
  erasedIf[DEBUG.type, T, U](f, g)

/** Performs an action (usually logging) only if debugging is enabled.
  * Equivalent to calling [[debugSwitch]] with an empty `g` argument.
  *
  * @param f
  *   The action to be performed
  */
inline def whenDebug(inline f: => Unit): Unit = debugSwitch { f } {}
