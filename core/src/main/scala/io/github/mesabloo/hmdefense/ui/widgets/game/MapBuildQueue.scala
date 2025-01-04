package io.github.mesabloo.hmdefense.ui.widgets.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureAtlas, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.ui.{Image, ScrollPane}
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.scenes.scene2d.{Actor, Group}
import com.badlogic.gdx.utils.{Align, Timer}
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.atlases.{GameAtlases, given}
import io.github.mesabloo.hmdefense.assets.textures.{GameTextures, given}
import io.github.mesabloo.hmdefense.ui.stage.VP_HEIGHT

import scala.collection.mutable

enum GameEntityMarker derives CanEqual:
  case BaseEntity(isAlly: Boolean)
  case TankEntity
  case TurretEntity
  case MachineEntity

private class Border(private val isHorizontal: Boolean)
    extends Image({
      val drawable = TiledDrawable(
        TextureRegion(
          assetManager.get(GameTextures.RadarBorder, classOf[Texture])
        )
      )

      if isHorizontal then drawable.setPadding(0f, 4f, 0f, 4f)
      else drawable.setPadding(4f, 0f, 4f, 0f)

      drawable
    })

trait BuildQueue:
  /** @return
    *   `true` if it is not possible to add another entry in the build queue,
    *   `false` otherwise.
    */
  def reachedCapacity: Boolean

  /** Indicate that a new build must be started.
    *
    * @param delay
    *   The time necessary (in seconds) to complete the build.
    * @param onDone
    *   The callback to trigger once the build is complete.
    * @return
    *   `true` if the build has successfully been enqueued, `false` otherwise.
    */
  def postBuild(delay: Float, onDone: () => Unit): Boolean

/** @tparam T
  *   The type of the actor within the `pane`.
  * @param terrain
  * @param pane
  */
class MapBuildQueue[T <: Actor](private val pane: ScrollPane) extends Group:
  /** A reference to the child of the [[this.pane]].
    */
  private val terrain = pane.getActor.asInstanceOf[T]

  object Map extends Group:
    /** The height of the minimap widget within this group. */
    private inline val MAP_HEIGHT = 151f

    /** The width of the minimap widget within this group. */
    private inline val MAP_WIDTH = 64f

    private object Radar:
      val borderTop: Border = Border(true)
      val borderBottom: Border = Border(true)
      val borderLeft: Border = Border(false)
      val borderRight: Border = Border(false)

      val markers: Group = Group()
      val markersMap: mutable.WeakHashMap[Actor, Image] =
        mutable.WeakHashMap()

    this.addActor(this.Radar.markers)
    // Borders on top of markers
    this.addActor(this.Radar.borderTop)
    this.addActor(this.Radar.borderBottom)
    this.addActor(this.Radar.borderLeft)
    this.addActor(this.Radar.borderRight)

    override def act(delta: Float): Unit =
      val viewHeight = math.scale(
        pane.getScrollHeight,
        0f,
        terrain.getHeight,
        0f,
        MAP_HEIGHT
      )
      val viewWidth = MAP_WIDTH

      this.setSize(MAP_WIDTH, MAP_HEIGHT + viewHeight)

      this.Radar.borderTop.setSize(viewWidth, 4f)
      this.Radar.borderBottom.setSize(viewWidth, 4f)
      this.Radar.borderLeft.setSize(4f, viewHeight)
      this.Radar.borderRight.setSize(4f, viewHeight)
      this.Radar.markers.setSize(MAP_WIDTH, MAP_HEIGHT + viewHeight)

      // X position within this actor of the map!
      inline val viewBottomX = 0f
      // Y position within this actor of the map!
      inline val viewBottomY = 0f

      // **** Update view in minimap ****

      val scrollPercentY = 1f - pane.getScrollPercentY
      val widthMiddlePoint = viewBottomX + viewWidth / 2f
      val heightMiddlePoint =
        viewBottomY + (MAP_HEIGHT * scrollPercentY) + viewHeight / 2f

      this.Radar.borderBottom.setPosition(
        widthMiddlePoint,
        viewBottomY + (MAP_HEIGHT * scrollPercentY),
        Align.center
      )
      this.Radar.borderTop.setPosition(
        widthMiddlePoint,
        viewBottomY + (MAP_HEIGHT * scrollPercentY) + viewHeight,
        Align.center
      )
      this.Radar.borderLeft.setPosition(
        viewBottomX,
        heightMiddlePoint,
        Align.center
      )
      this.Radar.borderRight.setPosition(
        viewBottomX + viewWidth,
        heightMiddlePoint,
        Align.center
      )
      this.Radar.markers.setPosition(viewBottomX, viewBottomY, Align.bottomLeft)

      // Update marker position of all tracked entities
      for (actor, img) <- this.Radar.markersMap
      do
        val x = math.scale(
          actor.getX(Align.center),
          0f,
          terrain.getWidth,
          0f,
          MAP_WIDTH
        )
        val y = math.scale(
          actor.getY(Align.center),
          0f,
          terrain.getHeight,
          0f,
          MAP_HEIGHT + viewHeight
        )
        img.setPosition(x, y, Align.center)

      super.act(delta)

    /** Must be called whenever a new entity must be tracked by the map. This
      * includes (but is not limited to) bases, machines, turrets, tanks.
      *
      * @param entity
      */
    def trackEntity(entity: Actor): Unit =
      assert(entity.getUserObject.isInstanceOf[GameEntityMarker])

      val img = Image(
        assetManager
          .get[TextureAtlas](GameAtlases.RadarMarks)
          .findRegion(entity.getUserObject.asInstanceOf[GameEntityMarker] match
            case GameEntityMarker.BaseEntity(true)  => "ally-base"
            case GameEntityMarker.BaseEntity(false) => "enemy-base"
            case GameEntityMarker.TankEntity        => "enemy"
            case GameEntityMarker.TurretEntity |
                GameEntityMarker.MachineEntity =>
              "ally"
          )
      )
      this.Radar.markersMap.update(entity, img)
      this.Radar.markers.addActor(img)

    /** Remove any marker associated to an entity.
      *
      * @param entity
      */
    def untrackEntity(entity: Actor): Unit =
      assert(entity.getUserObject.isInstanceOf[GameEntityMarker])

      this.Radar.markersMap
        .remove(entity)
        .foreach: img =>
          this.Radar.markers.removeActor(img, true)

  object BuildQueue extends Group with BuildQueue:
    /** The maximum number of concurrent builds allowed. */
    private final val MAX_QUEUE_SIZE = 7

    private val builds: Array[Actor] = Array.ofDim(MAX_QUEUE_SIZE)
    private val nbOfBuilds: Int = 0

    override def reachedCapacity: Boolean =
      nbOfBuilds == builds.length

    override def postBuild(delay: Float, onDone: () => Unit): Boolean =
      if nbOfBuilds == builds.length then return false
      // TODO: create queued item actor with progress bar
      Timer.instance().scheduleTask(() => onDone(), delay)
      true

  // Add the background, the map and the build queue
  this.addActor({
    val img = Image(
      assetManager.get(GameTextures.MapBuildQueue, classOf[Texture])
    )
    img.setHeight(VP_HEIGHT * 0.835f)

    img
  })
  this.addActor(this.Map)
  // TODO: build queue

  override def act(delta: Float): Unit =
    this.Map.setPosition(23f, 582f)
    super.act(delta)

  def map: Map.type = this.Map
  def buildQueue: BuildQueue.type = this.BuildQueue
