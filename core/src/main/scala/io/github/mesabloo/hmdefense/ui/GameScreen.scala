package io.github.mesabloo.hmdefense.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.{Image, ScrollPane}
import com.badlogic.gdx.scenes.scene2d.{Actor, Group, InputEvent, InputListener}
import io.github.mesabloo.hmdefense.app.GameEntryPoint
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.atlases.{GameAtlases, given}
import io.github.mesabloo.hmdefense.assets.fonts.{GameFonts, given}
import io.github.mesabloo.hmdefense.assets.textures.{GameTextures, given}
import io.github.mesabloo.hmdefense.config.{DEBUG, whenDebug}
import io.github.mesabloo.hmdefense.data.{GameSave, InvalidSaveException, MapGeometry, backgroundIndex}
import io.github.mesabloo.hmdefense.ui.stage.{MainStage, MapGeometryVisualizer, VP_HEIGHT, VP_WIDTH}
import io.github.mesabloo.hmdefense.ui.widgets.game.{BuildSlots, MapBuildQueue}

import scala.compiletime.uninitialized

@throws[InvalidSaveException]
class GameScreen(
    game: GameEntryPoint,
    private val currentLevel: Int,
    private val save: GameSave
) extends BaseScreen(game, true):
  // Check that the current save is valid, in case deserialization happened to mess something up (it shouldn't).
  this.save.checkValid()
  whenDebug:
    assert(this.save.lastUnlockedLevel + 1 >= this.currentLevel)

    Gdx.app.log(
      getClass.getCanonicalName,
      "Loading game for save:\n" +
        s"- Name = ${this.save.name}\n" +
        s"- Creation date = ${this.save.creationDate}\n" +
        s"- Credits = ${this.save.credits}\n" +
        s"- Machine upgrades = ${this.save.machineUpgrades}\n" +
        s"- Turret upgrades = ${this.save.turretUpgrades}\n" +
        s"- Upgrades = ${this.save.upgrades}\n" +
        s"- Build slots = ${this.save.buildSlots}"
    )

  /** The [[Group]] containing the background of the terrain, to which entities
    * are added (e.g. when spawning machines or turrets).
    */
  private var terrain: Group = uninitialized

  /** The left part of the game screen, containing the map as well as the build
    * queue.
    */
  // NOTE: The type parameter must be the same as the type of `this.terrain`!
  private var buildQueue: MapBuildQueue[Group] = uninitialized

  private var buildSlots: BuildSlots = uninitialized

  override protected val requiredTextures: Array[String] = Array(
    // The textures for the background
    GameTextures.Background(currentLevel, true, false, false),
    GameTextures.Background(currentLevel, false, false, false),
    GameTextures.MapBuildQueue,
    GameTextures.MachSlots,
    GameTextures.RadarBorder,
    GameTextures.BuildSlotBackground,
    GameTextures.BuildSlotForeground,
    GameTextures.BuildSlotCover,
    GameTextures.BuildSlotIndicatorBlue,
    GameTextures.BuildSlotIndicatorRed
  )

  override protected val requiredFonts: Array[String] = Array(
    GameFonts.CellCounter,
    GameFonts.CellTotal,
    GameFonts.BuildSlotName
  )

  override protected val requiredAtlases: Array[String] = Array(
    GameAtlases.RadarMarks
  )

  override protected val requiredAdditionalAssets: Map[String, Class[?]] = Map(
    f"data/maps/map-${backgroundIndex(this.currentLevel)}%02d.graph" -> classOf[
      MapGeometry
    ]
  )

  override protected def onFinishLoading(): Unit =
    whenDebug:
      assert(this.requiredTextures.forall(assetManager.isLoaded))

    // Setup the terrain
    val pane: ScrollPane = this.setupTerrain
    MainStage.addActor(pane)

    // Setup the build queue and map widgets
    MainStage.addActor({
      this.buildQueue = MapBuildQueue(pane)
      this.buildQueue.setPosition(0f, VP_HEIGHT * 0.165f)
      this.buildQueue
    })
    // Setup the build slots and special attacks widgets
    MainStage.addActor({
      this.buildSlots = BuildSlots(this.save, this.buildQueue.BuildQueue)
      this.buildSlots
        .setPosition(this.terrain.getParent.getWidth, VP_HEIGHT * 0.165f)
      this.buildSlots
    })
    // TODO: bottom menu (upgrades etc)

    // Start the cell mining process
    this.buildSlots.startMining()

  /** Creates the terrain background which is scrollable.
    *
    * @return
    *   The [[ScrollPane]] containing the [[this.terrain]].
    */
  private def setupTerrain: ScrollPane =
    val bg1 = Image(
      assetManager.get[Texture](
        GameTextures.Background(currentLevel, true, false, false)
      )
    )
    val bg2 = Image(
      assetManager.get[Texture](
        GameTextures.Background(currentLevel, false, false, false)
      )
    )
    bg2.setPosition(bg1.getX, bg1.getY + bg1.getHeight)

    val group = Group()
    group.setHeight(bg1.getHeight + bg2.getHeight)
    group.setWidth(math.max(bg1.getWidth, bg2.getWidth))
    group.addActor(bg1)
    group.addActor(bg2)
    whenDebug:
      group.addActor({
        val vis = MapGeometryVisualizer(
          assetManager.get[MapGeometry](
            f"data/maps/map-${backgroundIndex(this.currentLevel)}%02d.graph"
          ),
          group.getWidth,
          group.getHeight
        )
        vis.setPosition(0, 0)
        vis
      })

    this.terrain = group

    val pane = ScrollPane(group)
    pane.setBounds(
      VP_WIDTH / 2f - group.getWidth / 2f,
      VP_HEIGHT * 0.165f,
      2f / 3f * VP_WIDTH,
      VP_HEIGHT * 0.835f
    )
    pane.setSmoothScrolling(true)
    pane.setScrollbarsVisible(false)
    pane.setScrollingDisabled(true, false)
    pane.setOverscroll(false, false)
    pane.layout()
    pane.scrollTo(0f, 0f, pane.getWidth, pane.getHeight / 2f)
    // Allow scroll focus when mouse is within scroll pane
    pane.addListener(new InputListener {
      override def enter(
          event: InputEvent,
          x: Float,
          y: Float,
          pointer: Int,
          fromActor: Actor
      ): Unit =
        pane.getStage.setScrollFocus(pane)

      override def exit(
          event: InputEvent,
          x: Float,
          y: Float,
          pointer: Int,
          toActor: Actor
      ): Unit =
        pane.getStage.setScrollFocus(null)
    })

    pane
