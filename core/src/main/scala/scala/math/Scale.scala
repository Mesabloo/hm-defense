package scala.math

import scala.math.Fractional.Implicits.infixFractionalOps

/** Map a value `x` from a range `[from_min, from_max]` (bounds included) to a
  * range `[to_min, to_max]` (bounds included).
  */
inline def scale[T: Fractional](
    x: T,
    from_min: T,
    from_max: T,
    to_min: T,
    to_max: T
): T = (x - from_min) * (to_max - to_min) / (from_max - from_min) + to_min
