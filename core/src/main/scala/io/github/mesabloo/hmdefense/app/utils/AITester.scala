package io.github.mesabloo.hmdefense.app.utils

import com.badlogic.gdx.*
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.scenes.scene2d.ui.{HorizontalGroup, Skin, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.{Align, Logger, ScreenUtils}
import io.github.mesabloo.hmdefense.app.utils.Object.{Machine, Target}

import scala.collection.mutable
import scala.util.Random

private inline val MACHINE_WIDTH = 1.3f
private inline val TARGET_WIDTH = 0.8f

enum Object(
    private val fixture: Fixture,
    private val behavior: BehaviorTree[Object]
):
  case Machine(world: World, x: Float, y: Float, behavior: BehaviorTree[Object])
      extends Object(
        {
          val body = world.createBody({
            val `def` = BodyDef()
            `def`.`type` = BodyType.DynamicBody
            `def`.position.set(x, y)
            `def`
          })
          val shape = PolygonShape()
          shape.setAsBox(MACHINE_WIDTH / 2f, MACHINE_WIDTH / 2f)
//          shape.set(
//            Array(
//              Vector2(0, MACHINE_WIDTH * (math.sqrt(3f).toFloat / 3f)),
//              Vector2(
//                MACHINE_WIDTH / 2f,
//                -(math.sqrt(3f).toFloat / 6f) * MACHINE_WIDTH
//              ),
//              Vector2(
//                -MACHINE_WIDTH / 2f,
//                -(math.sqrt(3f).toFloat / 6f) * MACHINE_WIDTH
//              )
//            )
//          )

          val fixtureDef = FixtureDef()
          fixtureDef.shape = shape
          fixtureDef.density = 10f
          fixtureDef.friction = 5f

          val fixture = body.createFixture(fixtureDef)
          shape.dispose()
          fixture
        },
        behavior
      )
  case Target(world: World, x: Float, y: Float)
      extends Object(
        {
          val body = world.createBody({
            val `def` = BodyDef()
            `def`.`type` = BodyType.StaticBody
            `def`.position.set(x, y)
            `def`
          })

          val shape = CircleShape()
          shape.setRadius(TARGET_WIDTH / 2f)

          val fixtureDef = FixtureDef()
          fixtureDef.shape = shape
          fixtureDef.density = 100f

          val fixture = body.createFixture(fixtureDef)
          shape.dispose()
          fixture
        },
        BehaviorTree()
      )

  def getX: Float = this.fixture.getBody.getPosition.x
  def getY: Float = this.fixture.getBody.getPosition.y

  def draw(renderer: ShapeRenderer): Unit = this match
    case Machine(_, _, _, _) =>
      renderer.set(ShapeType.Line)

      val shape = this.fixture.getShape.asInstanceOf[PolygonShape]
      val origin = this.fixture.getBody.getPosition
      val transform = this.fixture.getBody.getTransform

      renderer.setColor(Color.LIME)
      for i <- 0 until shape.getVertexCount do
        val point = Vector2()
        shape.getVertex(i, point)
        transform.mul(point)
        renderer.x(point, 0.1f)
      // renderer.polygon(Array(A.x, A.y, B.x, B.y, C.x, C.y, D.x, D.y))
      renderer.setColor(Color.CORAL)
      renderer.x(origin.x, origin.y, 0.1f)
      {
        val A = Vector2()
        val B = Vector2()
        shape.getVertex(2, A)
        shape.getVertex(3, B)
        transform.mul(A)
        transform.mul(B)

        renderer.x((A.x + B.x) / 2f, (A.y + B.y) / 2f, 0.1f)
      }
    case Target(_, _, _) =>
      renderer.set(ShapeType.Line)

      val origin = this.fixture.getBody.getPosition

      renderer.setColor(Color.NAVY)
      renderer.circle(origin.x, origin.y, TARGET_WIDTH / 2f)
      renderer.setColor(Color.CORAL)
      renderer.x(origin.x, origin.y, 0.1f)

private class TesterScreen4 extends ScreenAdapter:
  // Scene2D

  private inline val VP_WIDTH = 1536f
  private inline val VP_HEIGHT = VP_WIDTH * 1.3f

  private val skin = Skin(Gdx.files.internal("utils/model-tester/uiskin.json"))

  private val stage = Stage(ExtendViewport(VP_WIDTH, VP_HEIGHT))
  // this.stage.setDebugAll(true)

  this.stage.addActor({
    val group = HorizontalGroup().pad(6f).space(6f)
    group.setWidth(this.stage.getWidth)

    group.addActor({
      val btn = TextButton("Spawn machine", skin)
      btn.addListener(new ClickListener:
        private val rnd = Random()

        override def clicked(event: InputEvent, x: Float, y: Float): Unit =
          TesterScreen4.this.objects.addOne(
            Machine(
              TesterScreen4.this.world,
              rnd.between(0f, 10f) * MACHINE_WIDTH + MACHINE_WIDTH / 2f,
              MACHINE_WIDTH / 2f,
              BehaviorTree()
            )
          )
      )
      btn
    })
    group.pack()
    group.setPosition(0f, this.stage.getHeight, Align.topLeft)

    group
  })

  // Box2D

  private inline val WORLD_WIDTH = 14.3f
  private inline val WORLD_HEIGHT = WORLD_WIDTH * 1.3f

  private val world = World(Vector2(0, 0), true)
  private val worldRenderer = Box2DDebugRenderer()
  private val worldViewport = ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT)

  private val objects = mutable.ArrayBuffer[Object]()
  private val objectsRenderer = ShapeRenderer()
  this.objectsRenderer.setAutoShapeType(true)

  private val inputMux = InputMultiplexer()
  inputMux.addProcessor(this.stage)
  inputMux.addProcessor(
    new InputAdapter:
      override def touchDown(
          screenX: Int,
          screenY: Int,
          pointer: Int,
          button: Int
      ): Boolean =
        button match
          case Input.Buttons.LEFT =>
            val pos = TesterScreen4.this.worldViewport.unproject(
              Vector2(screenX.toFloat, screenY.toFloat)
            )
            TesterScreen4.this.objects.addOne(
              Target(TesterScreen4.this.world, pos.x, pos.y)
            )
            true
          case _ => false
  )
  Gdx.input.setInputProcessor(inputMux)

  override def render(delta: Float): Unit =
    ScreenUtils.clear(Color.BLACK)

    this.world.step(1 / 60f, 6, 2)
    this.worldRenderer.render(this.world, this.worldViewport.getCamera.combined)

    this.stage.act(delta)
    this.stage.draw()

    this.objectsRenderer.begin(ShapeType.Line)
    this.objectsRenderer.setProjectionMatrix(
      this.worldViewport.getCamera.combined
    )
    for obj <- this.objects do obj.draw(this.objectsRenderer)
    this.objectsRenderer.end()

  override def resize(width: Int, height: Int): Unit =
    super.resize(width, height)

    this.stage.getViewport.update(width, height, true)
    this.worldViewport.update(width, height, true)

private class AITester extends Game:
  override def create(): Unit =
    Box2D.init()
    Gdx.app.setLogLevel(Logger.DEBUG)

    this.setScreen(TesterScreen4())
