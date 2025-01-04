package io.github.mesabloo.hmdefense.app.utils

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.math.{EarClippingTriangulator, Polygon}
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Actor, Group, InputEvent, Stage}
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.{Align, Logger, ScreenUtils}
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.data.MapGeometry
import io.github.mesabloo.hmdefense.ui.stage.{VP_HEIGHT, VP_WIDTH}
import spack.Spack

import java.io.{File, FileInputStream, FileOutputStream}
import scala.collection.mutable
import scala.reflect.ClassTag
import scala.util.Using

private class TesterScreen3 extends ScreenAdapter:
  private final inline val SIZE_FACTOR = 1.5f
  private val stage = Stage(
    FitViewport(VP_WIDTH * SIZE_FACTOR, VP_HEIGHT * SIZE_FACTOR)
  )
  private val bgInfo = HorizontalGroup().pad(6f).space(6f)
  private val bg = Group()
  private val bgPane = ScrollPane(Group())

  private val skin = Skin(Gdx.files.internal("utils/model-tester/uiskin.json"))

  private val level = SelectBox[Integer](skin)
  level.setItems({
    val arr = com.badlogic.gdx.utils.Array.of(false, 28, classOf[Integer])
    for i <- 1 to 27 do arr.insert(i - 1, Int.box(i))
    arr
  })

  private object MapObstacles extends Actor:
    private val renderer = ShapeRenderer()
    private val ear = new EarClippingTriangulator

    private val polys = mutable.ArrayBuffer[mutable.ArrayBuffer[Float]]()

    private[utils] var polygonStarted: Boolean = false

    override def draw(batch: Batch, parentAlpha: Float): Unit =
      super.draw(batch, parentAlpha)
      batch.end()

      renderer.setProjectionMatrix(batch.getProjectionMatrix)
      renderer.setTransformMatrix(batch.getTransformMatrix)
      Gdx.gl.glLineWidth(2f)
      renderer.setColor(Color.SCARLET)

      renderer.begin(ShapeType.Filled)
      // For all finished polygons
      for poly <- polys.dropRight(if this.polygonStarted then 1 else 0) do
        val arrRes = ear.computeTriangles(poly.toArray)
        for i <- 0.to(arrRes.size - 2, 3) do
          val x1 = poly(arrRes.get(i) * 2)
          val y1 = poly((arrRes.get(i) * 2) + 1)
          val x2 = poly(arrRes.get(i + 1) * 2)
          val y2 = poly((arrRes.get(i + 1) * 2) + 1)
          val x3 = poly(arrRes.get(i + 2) * 2)
          val y3 = poly((arrRes.get(i + 2) * 2) + 1)
          renderer.triangle(x1, y1, x2, y2, x3, y3)
      renderer.end()

      // If there is an unfinished polygon, do not fill it yet, only render its outline
      if this.polygonStarted then
        renderer.begin(ShapeType.Line)
        val poly = polys.last
        if poly.size == 2 then renderer.x(poly(0), poly(1), 4f)
        else if poly.size == 4 then
          renderer.line(poly(0), poly(1), poly(2), poly(3))
        else renderer.polygon(poly.toArray)
        renderer.end()

      batch.begin()

    override def clear(): Unit =
      this.polys.clear()
      this.polygonStarted = false

    def cancelPolygon(): Unit =
      if !this.polygonStarted then
        Gdx.app.error(getClass.getCanonicalName, "No polygon started")
      else
        this.polygonStarted = false
        this.polys.dropRightInPlace(1)

    def endPolygon(): Unit =
      if !this.polygonStarted then
        Gdx.app.error(getClass.getCanonicalName, "No polygon started")
      else if this.polys.last.size < 6 then
        Gdx.app.error(
          getClass.getCanonicalName,
          s"Polygon does not have enough points (at least 3 required, found ${this.polys.last.size / 2})"
        )
      else this.polygonStarted = false

    def makePoint(x: Float, y: Float): Unit =
      if this.polygonStarted then polys.last.append(x).append(y)
      else
        this.polygonStarted = true
        polys.append(mutable.ArrayBuffer(x, y))

    def removeLastPoint(): Unit =
      if !this.polygonStarted then
        Gdx.app.error(getClass.getCanonicalName, "No polygon started")
      else if this.polys.last.size <= 2 then
        this.polys.dropRightInPlace(1)
        this.polygonStarted = false
      else this.polys.last.dropRightInPlace(2)

    def polygons: Array[Array[Float]] =
      this.polys.map(_.toArray).toArray

    def setPolygons(polys: Array[Array[Float]]): Unit =
      this.polys.clear()
      for poly <- polys do this.polys.append(mutable.ArrayBuffer.from(poly))

    def tryRemovePolygon(x: Float, y: Float): Unit =
      if this.polygonStarted then
        Gdx.app.error(getClass.getCanonicalName, "Finish your polygon first")
      else
        this.polys.filterInPlace: poly =>
          !Polygon(poly.toArray).contains(x, y)

  private def pathFromLevel(level: Int): String =
    f"assets/data/maps/map-$level%02d.graph"

  this.bgInfo.setWidth(VP_WIDTH * SIZE_FACTOR)
  this.bgInfo.addActor(Label("Background number", skin))
  this.bgInfo.addActor(level)
  this.bgInfo.addActor({
    val btn = TextButton("New", skin)
    btn.addListener(
      new ClickListener:
        override def clicked(event: InputEvent, x: Float, y: Float): Unit =
          val group = TesterScreen3.this.bgPane.getActor.asInstanceOf[Group]
          group.clearChildren(true)

          val currentLevel = TesterScreen3.this.level.getSelected

          Gdx.app.debug(
            getClass.getCanonicalName,
            f"Loading backgrounds for index '$currentLevel%02d'"
          )

          assetManager.load(
            f"gfx/game/background/$currentLevel%02d/01.jpg",
            classOf[Texture]
          )
          assetManager.load(
            f"gfx/game/background/$currentLevel%02d/02.jpg",
            classOf[Texture]
          )

          val bg1 = Image(
            assetManager.finishLoadingAsset[Texture](
              f"gfx/game/background/$currentLevel%02d/01.jpg"
            )
          )
          val bg2 = Image(
            assetManager.finishLoadingAsset[Texture](
              f"gfx/game/background/$currentLevel%02d/02.jpg"
            )
          )

          bg1.setSize(
            VP_WIDTH * SIZE_FACTOR,
            (VP_WIDTH * SIZE_FACTOR / bg1.getWidth) * bg1.getHeight
          )
          bg2.setSize(
            VP_WIDTH * SIZE_FACTOR,
            (VP_WIDTH * SIZE_FACTOR / bg2.getWidth) * bg2.getHeight
          )

          group.setSize(
            scala.math.max(bg1.getWidth, bg2.getWidth),
            bg1.getHeight + bg2.getHeight
          )
          MapObstacles.setSize(group.getWidth, group.getHeight)
          group.addActor(bg1)
          group.addActor(bg2)
          group.addActor(MapObstacles)

          bg1.setPosition(0, 0)
          bg2.setPosition(bg1.getX, bg1.getX + bg1.getHeight)
          MapObstacles.setPosition(bg1.getX, bg1.getY)

          TesterScreen3.this.bgPane.layout()
          TesterScreen3.this.bgPane.setScrollPercentY(100f)

          MapObstacles.clear()
    )
    btn
  })
  this.bgInfo.addActor({
    val btn = TextButton("Load", skin)
    btn.addListener(
      new ClickListener:
        override def clicked(event: InputEvent, x: Float, y: Float): Unit =
          val group = TesterScreen3.this.bgPane.getActor.asInstanceOf[Group]
          group.clearChildren(true)

          val currentLevel = TesterScreen3.this.level.getSelected

          Gdx.app.debug(
            getClass.getCanonicalName,
            f"Loading backgrounds for index '$currentLevel%02d'"
          )

          assetManager.load(
            f"gfx/game/background/$currentLevel%02d/01.jpg",
            classOf[Texture]
          )
          assetManager.load(
            f"gfx/game/background/$currentLevel%02d/02.jpg",
            classOf[Texture]
          )

          val bg1 = Image(
            assetManager.finishLoadingAsset[Texture](
              f"gfx/game/background/$currentLevel%02d/01.jpg"
            )
          )
          val bg2 = Image(
            assetManager.finishLoadingAsset[Texture](
              f"gfx/game/background/$currentLevel%02d/02.jpg"
            )
          )

          bg1.setSize(
            VP_WIDTH * SIZE_FACTOR,
            (VP_WIDTH * SIZE_FACTOR / bg1.getWidth) * bg1.getHeight
          )
          bg2.setSize(
            VP_WIDTH * SIZE_FACTOR,
            (VP_WIDTH * SIZE_FACTOR / bg2.getWidth) * bg2.getHeight
          )

          group.setSize(
            scala.math.max(bg1.getWidth, bg2.getWidth),
            bg1.getHeight + bg2.getHeight
          )
          MapObstacles.setSize(group.getWidth, group.getHeight)
          group.addActor(bg1)
          group.addActor(bg2)
          group.addActor(MapObstacles)

          bg1.setPosition(0, 0)
          bg2.setPosition(bg1.getX, bg1.getX + bg1.getHeight)
          MapObstacles.setPosition(bg1.getX, bg1.getY)

          TesterScreen3.this.bgPane.layout()
          TesterScreen3.this.bgPane.setScrollPercentY(100f)

          Gdx.app.debug(
            getClass.getCanonicalName,
            f"Reading file 'assets/data/maps/map-$currentLevel%02d.graph'..."
          )
          val mapGeometry_ =
            Using(
              FileInputStream(
                File(f"assets/data/maps/map-$currentLevel%02d.graph")
              )
            ): fis =>
              Spack.unpackTo[MapGeometry](fis).toEither match
                case Left(value)  => throw value
                case Right(value) => value
          val mapGeometry = mapGeometry_.get

          val polys = mapGeometry.polys.map: poly =>
            for i <- 0.until(poly.length - 1, 2) do
              poly(i) = scala.math.scale(
                poly(i),
                mapGeometry.rangeX._1,
                mapGeometry.rangeX._2,
                0,
                group.getWidth
              )
              poly(i + 1) = scala.math.scale(
                poly(i + 1),
                mapGeometry.rangeY._1,
                mapGeometry.rangeY._2,
                0,
                group.getHeight
              )
            poly
          MapObstacles.setPolygons(polys)
    )
    btn
  })
  this.bgInfo.addActor({
    val btn = TextButton("Save", skin)
    btn.addListener(
      new ClickListener:
        override def clicked(event: InputEvent, x: Float, y: Float): Unit =
          if MapObstacles.polygonStarted then
            Gdx.app.error(
              getClass.getCanonicalName,
              "Finish the polygons before exporting the map geometry."
            )
            return

          val level: Int = TesterScreen3.this.level.getSelected
          val path = pathFromLevel(level)

          val group = TesterScreen3.this.bgPane.getActor.asInstanceOf[Group]

          Gdx.app.log(getClass.getCanonicalName, s"Writing file '$path'...")

          val mapPoly = MapGeometry(
            (0f, group.getWidth),
            (0f, group.getHeight),
            MapObstacles.polygons
          )

          Using(FileOutputStream(File(path))): fos =>
            fos.write(summon[spack.Pack[MapGeometry]].packToByteArray(mapPoly))
    )
    btn
  })
  this.bgInfo.pack()
  this.bgInfo.setPosition(0, VP_HEIGHT * SIZE_FACTOR, Align.topLeft)

  this.bg.setSize(VP_WIDTH * SIZE_FACTOR, VP_HEIGHT * SIZE_FACTOR)
  this.bg.addActor(this.bgPane)

  this.bgPane.setFillParent(true)
  this.bgPane.setFlickScroll(true)
  this.bgPane.setScrollingDisabled(true, false)
  this.bgPane.setOverscroll(false, false)
  this.bgPane.layout()

  this.bgPane.getActor.addListener(
    new ClickListener(-1):
      override def clicked(event: InputEvent, x: Float, y: Float): Unit =
        if event.getButton == Input.Buttons.LEFT then
          MapObstacles.makePoint(x, y)
        else if event.getButton == Input.Buttons.RIGHT then
          MapObstacles.tryRemovePolygon(x, y)
  )

  this.stage.addActor(this.bg)
  this.stage.addActor(this.bgInfo)

  val inputMux = InputMultiplexer()
  inputMux.addProcessor(this.stage)
  inputMux.addProcessor(
    new InputAdapter:
      override def keyDown(keycode: Int): Boolean = keycode match
        case Input.Keys.ESCAPE =>
          MapObstacles.cancelPolygon()
          true
        case Input.Keys.ENTER =>
          MapObstacles.endPolygon()
          true
        case Input.Keys.BACKSPACE =>
          MapObstacles.removeLastPoint()
          true
        case _ => false
  )
  Gdx.input.setInputProcessor(inputMux)

  // this.stage.setDebugAll(true)

  override def render(delta: Float): Unit =
    super.render(delta)
    ScreenUtils.clear(Color.BLACK)

    this.stage.act(delta)
    this.stage.draw()

  override def resize(width: Int, height: Int): Unit =
    super.resize(width, height)
    this.stage.getViewport.update(width, height, true)

private class MapTiler extends Game:
  override def create(): Unit =
    Gdx.app.setLogLevel(Logger.DEBUG)

    this.setScreen(TesterScreen3())
