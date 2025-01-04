package io.github.mesabloo.hmdefense.ui.stage

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{EarClippingTriangulator, Polygon}
import com.badlogic.gdx.scenes.scene2d.Actor
import io.github.mesabloo.hmdefense.data.MapGeometry

import scala.collection.mutable

class MapGeometryVisualizer(
    private val geometry: MapGeometry,
    width: Float,
    height: Float
) extends Actor:
  private val polygons: mutable.Buffer[Polygon] = mutable.ArrayBuffer[Polygon]()

  for poly <- geometry.polys do
    val verts = mutable.ArrayBuffer[Float]()
    for i <- 0.until(poly.length - 1, 2) do
      verts
        .append(
          math.scale(poly(i), geometry.rangeX._1, geometry.rangeX._2, 0f, width)
        )
        .append(
          math.scale(
            poly(i + 1),
            geometry.rangeY._1,
            geometry.rangeY._2,
            0f,
            height
          )
        )
    polygons.append(Polygon(verts.toArray))
  this.setSize(width, height)

  private val ear = new EarClippingTriangulator

  override def drawDebug(shapes: ShapeRenderer): Unit =
    if !getDebug then return

    shapes.set(ShapeType.Line)
    shapes.setColor(Color.SCARLET)

    for poly <- this.polygons do
      val arrRes = ear.computeTriangles(poly.getVertices)
      for i <- 0.to(arrRes.size - 2, 3) do
        val x1 = poly.getVertices.apply(arrRes.get(i) * 2)
        val y1 = poly.getVertices.apply((arrRes.get(i) * 2) + 1)
        val x2 = poly.getVertices.apply(arrRes.get(i + 1) * 2)
        val y2 = poly.getVertices.apply((arrRes.get(i + 1) * 2) + 1)
        val x3 = poly.getVertices.apply(arrRes.get(i + 2) * 2)
        val y3 = poly.getVertices.apply((arrRes.get(i + 2) * 2) + 1)
        shapes.triangle(x1, y1, x2, y2, x3, y3)
//      shapes.polygon(poly.getVertices)
