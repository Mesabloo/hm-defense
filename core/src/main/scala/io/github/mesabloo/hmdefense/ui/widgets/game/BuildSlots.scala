package io.github.mesabloo.hmdefense.ui.widgets.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label}
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, TextureRegionDrawable}
import com.badlogic.gdx.scenes.scene2d.{Group, InputEvent, Touchable}
import com.badlogic.gdx.utils.{Align, Timer}
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.fonts.{GameFonts, given}
import io.github.mesabloo.hmdefense.assets.textures.{GameTextures, given}
import io.github.mesabloo.hmdefense.data.*
import io.github.mesabloo.hmdefense.ui.stage.VP_HEIGHT

import scala.collection.mutable
import scala.ref.Ref

/** The indicators in the build slots, which light up depending on the amount of
  * turrets/machines in the build queue.
  */
private class BuildIndicator
    extends Image(
      assetManager.get[Texture](GameTextures.BuildSlotIndicatorBlue)
    ):
  private final val redTexture = TextureRegionDrawable(
    assetManager.get[Texture](GameTextures.BuildSlotIndicatorRed)
  )
  private final val blueTexture = this.getDrawable
//    TextureRegionDrawable(
//      assetManager.get[Texture](GameTextures.BuildSlotIndicatorBlue)
//    )

  /** Make the indicator red.
    */
  def on(): Unit =
    this.setDrawable(redTexture)

  /** Make the indicator blue.
    */
  def off(): Unit =
    this.setDrawable(blueTexture)

/** A clickable build slot, appearing in the right-hand menu.
  *
  * @param kind
  *   Which kind of entity to be built on click?
  */
private class BuildSlot(
    private val save: GameSave,
    private val queue: BuildQueue,
    private val kind: MachineKind | TurretKind
) extends Group:
  private val indicators: Array[BuildIndicator] = Array.fill(kind match
    case kind: MachineKind =>
      MachineBuilds(kind)(this.save.machineUpgrades(kind) - 1).maxUnits
    case kind: TurretKind => 1
  )(BuildIndicator())

  /** The number of side indicators that are lit up. */
  private var litIndicators: Int = 0

  this.setTouchable(Touchable.enabled)
  this.addActor({
    val img = Image(assetManager.get[Texture](GameTextures.BuildSlotForeground))
    this.setSize(img.getWidth, img.getHeight)
    img
  })
  this.addActor({
    val img = Image(assetManager.get[Texture](GameTextures.BuildSlotBackground))
    img.setPosition(
      this.getWidth / 2f + 19f,
      this.getHeight / 2f + 17f,
      Align.center
    )
    img
  })
  this.addActor({
    val txt = Label(
      // DO NOT REMOVE PARENTHESES: it messes with scalafmt otherwise
      (kind match
        case kind: MachineKind => kind.getName
        case kind: TurretKind  => kind.getName
      ),
      LabelStyle(assetManager.get[BitmapFont](GameFonts.BuildSlotName), null)
    )
    txt.setPosition(
      this.getWidth / 2f + 19f,
      39f,
      Align.center
    )
    txt
  })
  {
    val offsetX = 36f
    var offsetY = 62f
    for indicator <- this.indicators do
      indicator.setPosition(offsetX, offsetY)
      indicator.off()
      this.addActor(indicator)
      offsetY -= 12f
  }
  // TODO: add preview of machine/turret model

  private val cover: Image = Image(
    assetManager.get[Texture](GameTextures.BuildSlotCover)
  )
  this.addActor(cover)

  this.addListener(
    new ClickListener:
      override def clicked(event: InputEvent, x: Float, y: Float): Unit =
        // If click is inside the foreground sprite (this is not perfect as our sprite is not a rectangle, but good enough)
        // and the build slot is actually active (i.e. we can spawn more of the selected machine/turret)
        if x >= 34f && x <= 122f && y >= 11f && y <= 118f && !cover.isVisible && !BuildSlot.this.queue.reachedCapacity
        then
          BuildSlot.this.kind match
            case kind: MachineKind =>
              if BuildSlot.this.queue.postBuild(
                  MachineBuilds(kind)(
                    BuildSlot.this.save.machineUpgrades(kind) - 1
                  ).time,
                  () => {
                    BuildSlot.this.turnDownIndicator()
                    Gdx.app.debug(getClass.getCanonicalName, "spawning machine")
                  }
                )
              then BuildSlot.this.lightUpIndicator()
            case kind: TurretKind => ???
          super.clicked(event, x, y)
  )

  /** Make one more indicator lit up. */
  private def lightUpIndicator(): Unit =
    assert(this.litIndicators < this.indicators.length)
    this.indicators(this.litIndicators).on()
    this.litIndicators += 1

  /** Switch off the last indicator. */
  private def turnDownIndicator(): Unit =
    assert(this.litIndicators > 0)
    this.litIndicators -= 1
    this.indicators(this.litIndicators).off()

  override def act(delta: Float): Unit =
    val coverVisible =
      this.litIndicators == this.indicators.length || this.queue.reachedCapacity
    this.cover.setVisible(coverVisible)
    this.setTouchable(
      if coverVisible then Touchable.disabled else Touchable.enabled
    )
    super.act(delta)

class BuildSlots(
    private val save: GameSave,
    private val queue: BuildQueue
) extends Group:
  private var maxCells: Int = Upgrades
    .cellStorage(save.upgrades(UpgradeKind.CellStorage) - 1)
    .storage

  private var temporaryCellStorageUpgrade: Int = 0

  /** The current count of cells gathered.
    */
  var cellCounter = Ref(maxCells * 2 / 5)

  /** The time in milliseconds between two increments of the cell counter.
    */
  private inline val CELL_MINING_SPEED = 0.05f

  private inline val BACKGROUND_OFFSET = 50f

  /** The task in charge of increasing the cell counter, unless it has reached
    * its maximum, every [[CELL_MINING_SPEED]] milliseconds.
    */
  private object CellIncreaseTask extends Timer.Task:
    override def run(): Unit =
      val x = cellCounter.get
      if x < maxCells
      then cellCounter.set(x + 1)

  /** The label in charge of showing the current cell counter.
    */
  private object CellCounter
      extends Label(
        "00000",
        LabelStyle(
          assetManager.get[BitmapFont](GameFonts.CellCounter),
          null
        )
      ):
    this.setAlignment(Align.right)

    override def act(delta: Float): Unit =
      this.setText(s"${cellCounter.get}")
      super.act(delta)

  /** The label for the max cell counter.
    */
  private object CellTotal
      extends Label(
        "00000",
        LabelStyle(assetManager.get[BitmapFont](GameFonts.CellTotal), null)
      ):
    this.setAlignment(Align.left)

    override def act(delta: Float): Unit =
      this.setText(s"$maxCells")
      super.act(delta)

  /** The list of build slots that are shown. Its size must always be included
    * within the range `1..7` (after initialization of this widget).
    */
  private val buildSlots: mutable.Buffer[BuildSlot] = mutable.ArrayBuffer()

  this.addActor({
    val img = Image(assetManager.get[Texture](GameTextures.MachSlots))
    img.setHeight(VP_HEIGHT * 0.835f + BACKGROUND_OFFSET)

    img
  })
  this.addActor(this.CellCounter)
  this.addActor(this.CellTotal)
  {
    val offsetX = 116f // TODO: ideally, `this.getWidth - SOME_CONSTANT`
    var offsetY = 666f + 12f
    for kind <- this.save.buildSlots do
      this.addActor({
        val slot = BuildSlot(this.save, this.queue, kind)
        slot.setPosition(offsetX, offsetY, Align.bottomLeft)
        offsetY -= slot.getHeight - 26f
        slot
      })
  }

  override def act(delta: Float): Unit =
    this.CellCounter.setPosition(
      197f,
      774f + BACKGROUND_OFFSET,
      Align.bottomRight
    )
    this.CellTotal.setPosition(203f, 780f + BACKGROUND_OFFSET, Align.bottomLeft)

    this.maxCells = Upgrades
      .cellStorage(save.upgrades(UpgradeKind.CellStorage) - 1)
      .storage * math.pow(2, this.temporaryCellStorageUpgrade).toInt

    super.act(delta)

  /** Launch the timer [[CellIncreaseTask]] which mines cells at a fixed rate.
    */
  def startMining(): Unit =
    Timer.instance().scheduleTask(this.CellIncreaseTask, 0f, CELL_MINING_SPEED)

  /** Stop the timer [[CellIncreaseTask]], effectively stopping mining of cells.
    */
  def stopMining(): Unit =
    this.CellIncreaseTask.cancel()
