package io.github.mesabloo.hmdefense.data

import spack.Message

import scala.reflect.ClassTag

given [T: spack.Pack]: spack.Pack[Array[T]] = arr =>
  Message.Arr(arr.map(x => summon[spack.Pack[T]].packToMessage(x)).toIndexedSeq)

given [T: spack.Unpack: ClassTag]: spack.Unpack[Array[T]] =
  case Message.Arr(seq) =>
    seq.map(x => summon[spack.Unpack[T]].unpackUnsafe(x)).toArray
  case _ => throw spack.UnpackException("Expected array")

given [T: spack.Pack, U: spack.Pack]: spack.Pack[(T, U)] = spack.Pack.derived

given [T: spack.Unpack, U: spack.Unpack]: spack.Unpack[(T, U)] =
  spack.Unpack.derived

/** Obstacles of a given map kind.
  *
  * @param polys
  *   The set of polygons (stored as an array of vertices `[x1, y1, x2, y2,...]`,
  *   as in [[com.badlogic.gdx.math.Polygon]]) that form
  *   obstacles on the map. X coordinates must be included in the range `rangeX`
  *   and Y coordinates must be included in the range `rangeY`.
  */
case class MapGeometry(
    rangeX: (Float, Float),
    rangeY: (Float, Float),
    polys: Array[Array[Float]]
) derives spack.Pack,
      spack.Unpack
