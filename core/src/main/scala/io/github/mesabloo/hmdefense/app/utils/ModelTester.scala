package io.github.mesabloo.hmdefense.app.utils

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{TextureAtlas, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.{ActorGestureListener, ClickListener}
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.{Align, Logger, ScreenUtils}
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.data.models.MachineModel

import java.io.{File, FileOutputStream}
import scala.compiletime.uninitialized
import scala.util.Using

private class MachineForm(
    skin: Skin,
    selectBody: (String, Int) => Unit,
    preview: Group
) extends VerticalGroup:
  private val machineLevel = SelectBox[Int](skin)
  machineLevel.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  machineLevel.setWidth(300f)
  private val machineBody = SelectBox[String](skin)
  machineBody.setItems(
    "heavy-missile",
    "hmg",
    "ion",
    "missile",
    "plasma",
    "rifle",
    "shotgun",
    "tanker"
  )
  machineBody.setWidth(300f)

  this.pad(10f, 3f, 10f, 3f)
  this.space(10f)

  this.addActor({
    val group = HorizontalGroup()
    group.addActor(Label("Machine level:", skin))
    group.addActor(machineLevel)
    group.space(5f)
  })
  this.addActor({
    val group = HorizontalGroup()
    group.addActor(Label("Machine body and weapons:", skin))
    group.addActor(this.machineBody)
    // group.addActor(ok)
    group.space(5f).padBottom(30f)
  })
  this.addActor({
    val group = HorizontalGroup()

    val loadButton = TextButton("Load", skin)
    loadButton.addListener(
      new ClickListener:
        override def clicked(event: InputEvent, x: Float, y: Float): Unit =
          selectBody(machineBody.getSelected, machineLevel.getSelected)
    )

    val saveButton = TextButton("Save", skin)
    saveButton.addListener(
      new ClickListener:
        override def clicked(event: InputEvent, x: Float, y: Float): Unit =
          val body = Option[Image](preview.findActor(BODY_NAME))
          val lweapon = Option[Image](preview.findActor(LWEAPON_NAME))
          val rweapon = Option[Image](preview.findActor(RWEAPON_NAME))

          body.lazyZip(lweapon).lazyZip(rweapon).headOption match
            case Some((body, lweapon, rweapon)) =>
              val bodyCenter =
                (body.getX(Align.center), body.getY(Align.center))
              val lweaponCenter =
                (lweapon.getX(Align.center), lweapon.getY(Align.center))
              val rweaponCenter =
                (rweapon.getX(Align.center), rweapon.getY(Align.center))

              val lwoff = (
                ((lweaponCenter._1 - bodyCenter._1) / SCALE_FACTOR).floor.toInt,
                ((lweaponCenter._2 - bodyCenter._2) / SCALE_FACTOR).floor.toInt
              )
              val rwoff = (
                ((rweaponCenter._1 - bodyCenter._1) / SCALE_FACTOR).floor.toInt,
                ((rweaponCenter._2 - bodyCenter._2) / SCALE_FACTOR).floor.toInt
              )

              Gdx.app
                .debug(getClass.getCanonicalName, s"Body found at $bodyCenter")
              Gdx.app
                .debug(
                  getClass.getCanonicalName,
                  s"Left weapon found at $lweaponCenter (offset of $lwoff)"
                )
              Gdx.app
                .debug(
                  getClass.getCanonicalName,
                  s"Right weapon found at $rweaponCenter (offset of $rwoff)"
                )

              val data = MachineModel(
                machineLevel.getSelected,
                machineBody.getSelected,
                lwoff,
                rwoff,
                0,
                (0, 0), // TODO
                (0, 0) // TODO
              )

              Gdx.app.log(
                getClass.getCanonicalName,
                s"Saving model of machine ${machineBody.getSelected} level ${machineLevel.getSelected}"
              )

              Using(
                FileOutputStream(
                  File(
                    s"assets/data/models/machines/${machineBody.getSelected}-${showLevel(machineLevel.getSelected)}.model"
                  )
                )
              ): fos =>
                fos.write(
                  summon[spack.Pack[MachineModel]].packToByteArray(data)
                )
                fos.flush()
            case None =>
              Gdx.app.error(
                getClass.getCanonicalName,
                "Inconsistent application state (missing sprites)"
              )
    )

    group.addActor(loadButton)
    group.addActor(saveButton)
    group.space(10f)
  })

private inline def showLevel(level: Int): String = f"$level%02d"

private inline val BODY_NAME = "machine-body"
private inline val LWEAPON_NAME = "lweapon"
private inline val RWEAPON_NAME = "rweapon"

private inline val SCALE_FACTOR = 3

/** The main screen of our Model Tester app.
  */
private class TesterScreen extends Screen:
  private val skin = new Skin(
    Gdx.files.internal("utils/model-tester/uiskin.json")
  )

  private def loadMachine(name: String, level: Int): Unit =
    def removeImage(name: String): Unit =
      Option(this.preview.findActor(name)).foreach(this.preview.removeActor)

    removeImage(BODY_NAME)
    removeImage(LWEAPON_NAME)
    removeImage(RWEAPON_NAME)

    val path = s"assets/data/models/machines/$name-${showLevel(level)}.model"
    assetManager.load(path, classOf[MachineModel])
    assetManager.finishLoading()
    val data: Option[MachineModel] = Some(assetManager.get(path))

    val body = Option(
      this.machineBodiesAtlas.findRegion(s"$name-${showLevel(level)}")
    )
    val weaponL = Option(
      this.machineWeaponsAtlas.findRegion(s"$name-${showLevel(level)}")
    )
    val weaponR = weaponL.map(TextureRegion(_))
    weaponR.foreach(_.flip(false, true))

    def makeImage(texture: TextureRegion, name: String, x: Int, y: Int): Image =
      val image = Image(texture)
      image.setName(name)
      image.setOrigin(Align.center)
      image.scaleBy(SCALE_FACTOR)
      image.setPosition(
        (this.preview.getWidth / 2f - image.getWidth / 2f).floor + x * SCALE_FACTOR,
        (this.preview.getHeight / 2f - image.getHeight / 2f).floor + y * SCALE_FACTOR
      )
      image.addListener(
        new ActorGestureListener():
          override def pan(
              event: InputEvent,
              x: Float,
              y: Float,
              deltaX: Float,
              deltaY: Float
          ): Unit =
            image.moveBy(
              (deltaX * image.getScaleX).floor,
              (deltaY * image.getScaleY).floor
            )
      )
      image

    data.lazyZip(body).lazyZip(weaponL).lazyZip(weaponR).headOption match
      case Some((data, body, weaponL, weaponR)) =>
        Gdx.app.log(getClass.getCanonicalName, s"Using model $data")

        this.preview.addActor(makeImage(body, BODY_NAME, 0, 0))
        this.preview.addActor(
          makeImage(weaponL, LWEAPON_NAME, data.lwoff._1, data.lwoff._2)
        )
        this.preview.addActor(
          makeImage(weaponR, RWEAPON_NAME, data.rwoff._1, data.rwoff._2)
        )
      case None =>
        Gdx.app.error(
          getClass.getCanonicalName,
          s"Machine '$name' (lvl $level) not found"
        )

  private val stage = Stage(ScreenViewport())
  private val preview = Group()
  private val selector: TabbedPane =
    TabbedPane(
      skin,
      Map(
        "Machine" -> {
          val form = MachineForm(skin, loadMachine, this.preview).pad(10f)
          form.setFillParent(true)
          form
        },
        "Turret" -> Label("Hello 2", skin) // TODO
      ),
      name => preview.clear()
    )

//  private val assetManager = AssetManager()
//  assetManager.setLoader(
//    classOf[MachineModel],
//    MachineModelLoader(InternalFileHandleResolver())
//  )

  private inline def textures = Array(
    "gfx/models/machines/bodies.atlas",
    "gfx/models/machines/feet.atlas",
    "gfx/models/machines/weapons.atlas"
  )

  private var machineBodiesAtlas: TextureAtlas = uninitialized
  private var machineFeetAtlas: TextureAtlas = uninitialized
  private var machineWeaponsAtlas: TextureAtlas = uninitialized

  private inline final def allLoaded: Boolean =
    this.textures.forall(assetManager.isLoaded)

  override def show(): Unit =
    this.textures.foreach(assetManager.load(_, classOf[TextureAtlas]))

    this.preview.setSize(this.stage.getWidth, this.stage.getHeight * 2f / 3f)
    this.preview.setPosition(0, this.stage.getHeight * 1f / 3f)
    this.preview.addListener(
      new InputListener:
        override def enter(
            event: InputEvent,
            x: Float,
            y: Float,
            pointer: Int,
            fromActor: Actor
        ): Unit =
          // stage.setScrollFocus(preview)
          stage.setKeyboardFocus(null)

        /*override def exit(
          event: InputEvent,
          x: Float,
          y: Float,
          pointer: Int,
          toActor: Actor
      ): Unit =
        stage.setScrollFocus(null)*/
    )
    this.preview.setTouchable(Touchable.enabled)
    this.preview.setOrigin(Align.center)

    this.selector.setSize(this.stage.getWidth, this.stage.getHeight * 1f / 3f)
    this.selector.setPosition(0, 0)

    this.stage.addActor(this.preview)
    this.stage.addActor(this.selector)

    val inputMux = InputMultiplexer()
    inputMux.addProcessor(this.stage)
    inputMux.addProcessor(
      new InputAdapter:
        override def keyDown(keycode: Int): Boolean =
          keycode match
            case Input.Keys.D =>
              stage.setDebugAll(!stage.isDebugAll)
              true
            case _ => false
    )
    Gdx.input.setInputProcessor(inputMux)

  private var texturesLoaded = false

  override def render(delta: Float): Unit =
    ScreenUtils.clear(Color.BLACK)

    if texturesLoaded then
      this.stage.act(delta)
      this.stage.draw()
    else if this.allLoaded then
      this.machineBodiesAtlas =
        assetManager.get("gfx/models/machines/bodies.atlas")
      this.machineFeetAtlas = assetManager.get("gfx/models/machines/feet.atlas")
      this.machineWeaponsAtlas =
        assetManager.get("gfx/models/machines/weapons.atlas")

      texturesLoaded = true
    else assetManager.update()

  override def resize(width: Int, height: Int): Unit =
    this.stage.getViewport.update(width, height, true)

    this.preview.setSize(this.stage.getWidth, this.stage.getHeight * 2f / 3f)
    this.preview.setPosition(0, this.stage.getHeight * 1f / 3f)

    this.selector.setSize(this.stage.getWidth, this.stage.getHeight * 1f / 3f)
    this.selector.setPosition(0, 0)

  override def pause(): Unit = ()

  override def resume(): Unit = ()

  override def hide(): Unit = ()

  override def dispose(): Unit =
    this.stage.dispose()

/** The entry point of our Model Tester app.
  */
private class ModelTester extends Game:
  override def create(): Unit =
    Gdx.app.setLogLevel(Logger.DEBUG)

    this.setScreen(TesterScreen())
