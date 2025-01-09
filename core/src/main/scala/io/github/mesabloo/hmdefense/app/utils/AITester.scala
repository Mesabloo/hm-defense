package io.github.mesabloo.hmdefense.app.utils

import com.badlogic.gdx.*
import com.badlogic.gdx.ai.GdxAI
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.ai.btree.utils.{BehaviorTreeLibraryManager, PooledBehaviorTreeLibrary}
import com.badlogic.gdx.ai.steer.behaviors.*
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration
import com.badlogic.gdx.ai.steer.{Steerable, SteeringAcceleration, SteeringBehavior}
import com.badlogic.gdx.ai.utils.{Collision, Location, Ray, RaycastCollisionDetector}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{MathUtils, Vector2}
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.scenes.scene2d.ui.{HorizontalGroup, Skin, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.{Align, Logger, ScreenUtils}

import scala.collection.mutable
import scala.util.Random

private inline val MACHINE_WIDTH = 1.3f
private inline val TARGET_WIDTH = 0.8f

/** A raycast collision detector for box2d.
  *
  * @see [[https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/Box2dRaycastCollisionDetector.java Some GDX AI test]]
  */
object Box2dRaycastCollisionDetector:
  class Box2dRaycastCallback extends RayCastCallback:
    private[Box2dRaycastCollisionDetector] var outputCollision
        : Option[Collision[Vector2]] = None
    private[Box2dRaycastCollisionDetector] var collided = false

    override def reportRayFixture(
        fixture: Fixture,
        point: Vector2,
        normal: Vector2,
        fraction: Float
    ): Float =
      if fixture.getUserData.asInstanceOf[Boolean] then
        this.outputCollision.foreach(_.set(point, normal))
        this.collided = true
        fraction
      else -1

final class Box2dRaycastCollisionDetector(
    private val world: World,
    private val callback: Box2dRaycastCollisionDetector.Box2dRaycastCallback
) extends RaycastCollisionDetector[Vector2] {
  def this(world: World) =
    this(world, Box2dRaycastCollisionDetector.Box2dRaycastCallback())

  override def collides(ray: Ray[Vector2]): Boolean = findCollision(null, ray)

  override def findCollision(
      outputCollision: Collision[Vector2],
      inputRay: Ray[Vector2]
  ): Boolean =
    callback.collided = false
    if !inputRay.start.epsilonEquals(
        inputRay.end,
        MathUtils.FLOAT_ROUNDING_ERROR
      )
    then
      callback.outputCollision = Option(outputCollision)
      world.rayCast(callback, inputRay.start, inputRay.end)
    callback.collided
}

final class Box2DLocation(private val position: Vector2)
    extends Location[Vector2] {
  private var orientation: Float = .0

  def this() = this(Vector2())

  override def getPosition: Vector2 = this.position

  def getOrientation: Float = this.orientation

  def setOrientation(orientation: Float): Unit = {
    this.orientation = orientation
  }

  def newLocation = Box2DLocation()

  def vectorToAngle(vector: Vector2): Float = vector.angleRad()

  def angleToVector(outVector: Vector2, angle: Float): Vector2 =
    outVector.set(-scala.math.sin(angle).toFloat, scala.math.cos(angle).toFloat)
}

final class Forward[T <: math.Vector[T]](
    owner: Steerable[T],
    private var direction: T
) extends SteeringBehavior[T](owner):
  override def calculateRealSteering(
      steering: SteeringAcceleration[T]
  ): SteeringAcceleration[T] =
    steering.linear.set(this.direction)
    steering

  /** Sets a new direction to follow. */
  def setDirection(newDirection: T): Forward[T] =
    this.direction = newDirection
    this

  override def setEnabled(enabled: Boolean): Forward[T] =
    super.setEnabled(enabled).asInstanceOf[Forward[T]]

sealed abstract class GameObject(
    private val world: World,
    val objects: mutable.Buffer[GameObject],
    private val fixture: Fixture,
    behavior_ : Option[GameObject => BehaviorTree[GameObject]],
    private var durability: Float,
    val range: Option[(Float, Float)]
) extends Steerable[Vector2]:
  private val behavior = behavior_.map(_(this))
  private var target: Option[GameObject] = None

  this.objects.addOne(this)

  protected val steering: Option[SteeringBehavior[Vector2]]

  protected final val steeringAcceleration = SteeringAcceleration(Vector2())

  /** Computes steering, if a [[SteeringBehavior]] is specified in [[GameObject.steering]].
    *
    * @return
    */
  private def computeSteering(): SteeringAcceleration[Vector2] =
    this.steering.foreach(_.calculateSteering(this.steeringAcceleration))
    this.steeringAcceleration

  def hasTarget: Boolean = this.target.isDefined

  def forgetTarget(): Unit = this.target = None

  def target(newTarget: GameObject): Unit =
    this.target match
      case Some(value) =>
        throw IllegalArgumentException(
          "Cannot target new object without forgetting about the old one."
        )
      case None => this.target = Some(newTarget)

  def getTarget: GameObject = this.target.get

  def isAlive: Boolean = this.durability > 1e-6

  protected def body: Body = this.fixture.getBody

  def draw(renderer: ShapeRenderer): Unit =
    val origin = this.body.getPosition

    this match
      case _: Machine =>
        renderer.set(ShapeType.Line)

        val shape = this.fixture.getShape.asInstanceOf[PolygonShape]
        val transform = this.body.getTransform

        renderer.setColor(Color.LIME)
        for i <- 0 until shape.getVertexCount do
          val point = Vector2()
          shape.getVertex(i, point)
          transform.mul(point)
          renderer.x(point, 0.1f)
        // renderer.polygon(Array(A.x, A.y, B.x, B.y, C.x, C.y, D.x, D.y))
        {
          val A = Vector2()
          val B = Vector2()
          shape.getVertex(2, A)
          shape.getVertex(3, B)
          transform.mul(A)
          transform.mul(B)

          renderer.x((A.x + B.x) / 2f, (A.y + B.y) / 2f, 0.1f)
        }
      case _: Target =>
        renderer.set(ShapeType.Line)

        renderer.setColor(Color.NAVY)
        renderer.circle(origin.x, origin.y, TARGET_WIDTH / 2f)
//      case _: GameObject =>
//        throw IllegalStateException("Unknown subclass of GameObject")

    renderer.setColor(Color.CORAL)
    renderer.x(origin.x, origin.y, 0.1f)

    this.range.foreach: (min, max) =>
      renderer.setColor(Color.YELLOW)
      renderer.circle(origin.x, origin.y, min, 40)
      renderer.setColor(Color.ORANGE)
      renderer.circle(origin.x, origin.y, max, 40)

    this.target.foreach: tgt =>
      renderer.setColor(Color.TEAL)
      renderer.line(this.getPosition, tgt.getPosition)

  def step(delta: Float): Unit =
    this.behavior.foreach(_.step())
    this.computeSteering()

  override def getBoundingRadius: Float = ???

  override def isTagged: Boolean = ???

  override def setTagged(tagged: Boolean): Unit = ???

  override def getZeroLinearSpeedThreshold: Float = 0.001f

  override def setZeroLinearSpeedThreshold(value: Float): Unit = ???

  override def setMaxLinearSpeed(maxLinearSpeed: Float): Unit = ???

  override def getMaxLinearAcceleration: Float = 1f

  override def setMaxLinearAcceleration(maxLinearAcceleration: Float): Unit =
    ???

  override def setMaxAngularSpeed(maxAngularSpeed: Float): Unit = ???

  override def getMaxAngularAcceleration: Float = 1f

  override def setMaxAngularAcceleration(maxAngularAcceleration: Float): Unit =
    ???

  override def setOrientation(orientation: Float): Unit =
    // this.body.setTransform(getPosition, orientation)
    ()

  override def newLocation(): Location[Vector2] = Box2DLocation()

  override def getPosition: Vector2 = this.body.getPosition

  override def getLinearVelocity: Vector2 = this.body.getLinearVelocity

  override def getAngularVelocity: Float = this.body.getAngularVelocity

  // override def getMaxLinearSpeed = 4f

  override def getOrientation: Float = this.body.getAngle + 90f.toRadians

  override def vectorToAngle(vector: Vector2): Float = vector.angleRad()

  override def angleToVector(outVector: Vector2, angle: Float): Vector2 =
    outVector.set(-scala.math.sin(angle).toFloat, scala.math.cos(angle).toFloat)

final class Machine(
    world: World,
    objects: mutable.Buffer[GameObject],
    x: Float,
    y: Float
) extends GameObject(
      world,
      objects, {
        val body = world.createBody({
          val `def` = BodyDef()
          `def`.`type` = BodyType.DynamicBody
          `def`.position.set(x, y)
          `def`.angle = 0f
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
      Some(obj =>
        BehaviorTreeLibraryManager
          .getInstance()
          .createBehaviorTree("ai/machine.btree", obj)
      ),
      1000f,
      Some((6f, 8f))
    ):
  /** Go forward. */
  private val fwd: Forward[Vector2] =
    Forward(this, Vector2(0f, 1f)) // Go in the Y direction (up)
      .setEnabled(true)

  /** Face the direction you are going. */
  private val lwyag: LookWhereYouAreGoing[Vector2] =
    LookWhereYouAreGoing(this)
      // .setTimeToTarget(1f / 6f)
      .setEnabled(true)

  /** Go toward a target, when there is one. */
  private val seek: Seek[Vector2] = Seek(this).setEnabled(false)

  /** In case there is a target, look at it. */
  private val face: Face[Vector2] = Face(this)
    // .setTimeToTarget(1f / 15f)
    .setEnabled(false)

  /** Avoid obstacles, by the way. */
  private val avoidObstacles = RaycastObstacleAvoidance(
    this,
    CentralRayWithWhiskersConfiguration(
      this,
      MACHINE_WIDTH * 2f,
      MACHINE_WIDTH + 1f / 3f * MACHINE_WIDTH,
      30f.toRadians
    ),
    Box2dRaycastCollisionDetector(world)
  ).setEnabled(true)

  override protected val steering: Option[SteeringBehavior[Vector2]] = Some(
    BlendedSteering(this)
      .add(PrioritySteering(this).add(face).add(lwyag).setEnabled(true), 1f)
      .add(
        PrioritySteering(this)
          .add(avoidObstacles)
          .add(seek)
          .add(fwd)
          .setEnabled(true),
        1f
      )
  )

  override def getMaxLinearSpeed: Float = 5f

  override def getMaxAngularSpeed: Float = 6f

  override def getMaxAngularAcceleration: Float = 2f

  override def forgetTarget(): Unit =
    super.forgetTarget()
    this.seek.setEnabled(false)
    this.face.setEnabled(false)
    this.fwd.setEnabled(true)
    this.avoidObstacles.setEnabled(true)

  override def target(newTarget: GameObject): Unit =
    super.target(newTarget)
    this.fwd.setEnabled(false)
    this.seek.setTarget(newTarget).setEnabled(true)
    this.face.setTarget(newTarget).setEnabled(true)
    this.avoidObstacles.setEnabled(true)

  /** Drive forward, in the direction of the current angle.
    */
  def walk(): Unit =
    this.body.setLinearVelocity(this.steeringAcceleration.linear)
    this.body.setAngularVelocity(this.steeringAcceleration.angular)

  /** Cancel all X and Y forces applied to the inner body,
    * so that it stops in place (but still keeps spinning).
    */
  def stopInPlace(): Unit =
    this.body.setLinearVelocity(Vector2.Zero)
    this.body.setAngularVelocity(0f)
    this.fwd.setEnabled(false)
    this.seek.setEnabled(false)
    this.face.setEnabled(true)
    this.avoidObstacles.setEnabled(false)

final class Target(
    world: World,
    objects: mutable.Buffer[GameObject],
    x: Float,
    y: Float
) extends GameObject(
      world,
      objects, {
        val body = world.createBody({
          val `def` = BodyDef()
          `def`.`type` = BodyType.StaticBody
          `def`.position.set(x, y)
          `def`.angle = 0f
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
      None,
      10f,
      None
    ):
  override protected val steering: Option[SteeringBehavior[Vector2]] = None

  override def getMaxLinearSpeed: Float = 0f

  override def getMaxAngularSpeed: Float = 0f

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
              TesterScreen4.this.objects,
              rnd.between(0f, 10f) * MACHINE_WIDTH + MACHINE_WIDTH / 2f,
              MACHINE_WIDTH / 2f
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
  private val worldRenderer =
    Box2DDebugRenderer(true, true, true, true, true, true)
  private val worldViewport = ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT)

  private val objects = mutable.ArrayBuffer[GameObject]()
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
              Target(
                TesterScreen4.this.world,
                TesterScreen4.this.objects,
                pos.x,
                pos.y
              )
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
    for obj <- this.objects do
      obj.step(GdxAI.getTimepiece.getDeltaTime)
      obj.draw(this.objectsRenderer)
    this.objectsRenderer.end()

  override def resize(width: Int, height: Int): Unit =
    super.resize(width, height)

    this.stage.getViewport.update(width, height, true)
    this.worldViewport.update(width, height, true)

private class AITester extends Game:
  override def create(): Unit =
    Box2D.init()
    Gdx.app.setLogLevel(Logger.DEBUG)
    BehaviorTreeLibraryManager.getInstance.setLibrary(
      PooledBehaviorTreeLibrary()
    )

    this.setScreen(TesterScreen4())
