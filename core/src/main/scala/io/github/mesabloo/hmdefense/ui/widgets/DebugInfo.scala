package io.github.mesabloo.hmdefense.ui.widgets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Timer
import com.sun.management.OperatingSystemMXBean
import io.github.mesabloo.hmdefense.assets.assetManager
import io.github.mesabloo.hmdefense.assets.fonts.{DebugFonts, given}

import java.lang.management.ManagementFactory

final class DebugInfo(
    lowFPSThreshold: Int,
    highFPSThreshold: Int,
    lowCPUThreshold: Double,
    highCPUThreshold: Double,
    lowRAMThreshold: Double,
    highRAMThreshold: Double,
    updateFrequencyMs: Float = 1000f
) extends Table():
  private final val fps = Label(
    "XXX FPS",
    LabelStyle(assetManager.get(DebugFonts.FPSFont), Color.WHITE)
  )
  private final val cpu = Label(
    "XXX.X%",
    LabelStyle(assetManager.get(DebugFonts.CPUFont), Color.WHITE)
  )
  private final val ram = Label(
    "XXX MB used",
    LabelStyle(assetManager.get(DebugFonts.RAMFont), Color.WHITE)
  )
  private final val sysInfo = Label(
    "Running on XXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
    LabelStyle(assetManager.get(DebugFonts.SystemInfoFont), Color.WHITE)
  )

  // this.setHeight(sysInfo.getPrefHeight)
  this.setRound(false)
  this.pad(2f, 4f, 2f, 4f)
  this.add(fps).fill().left().space(6f).uniformX()
  this.add(cpu).fill().left().space(6f).uniformX()
  this.add(ram).width(ram.getWidth).fill().left().space(6f).uniformX()
  this.add(sysInfo).fill().expandX().center().space(6f)
  this.pack()

  {
    val bgPixmap = Pixmap(
      this.getPrefWidth.toInt,
      this.getPrefHeight.toInt,
      Pixmap.Format.RGBA8888
    )
    bgPixmap.setColor(Color.BLACK)
    bgPixmap.fill()

    this.setBackground(TextureRegionDrawable(Texture(bgPixmap)))

    bgPixmap.dispose()
  }

  /////////////////////////////////

  private final val memoryMXBean = ManagementFactory.getMemoryMXBean
  private final val osMXBean =
    ManagementFactory.getPlatformMXBean(classOf[OperatingSystemMXBean])
  private final val runtimeMXBean = ManagementFactory.getRuntimeMXBean

//  whenDebug:
//    val mbeanServer = ManagementFactory.getPlatformMBeanServer
//    val mbeans = mbeanServer.queryNames(null, null)
//    for bean <- mbeans.asScala do
//      val info = mbeanServer.getMBeanInfo(bean)
//      val attrInfo = info.getAttributes
//
//      println(s"Attributes of $bean:")
//      for attr <- attrInfo do println(s"  ${attr.getName} : ${attr.getType}")

  private def getProcessCpuLoad: Double =
    val value = osMXBean.getProcessCpuLoad

    if value < 0f then Double.NaN
    else (value * 1000).toInt / 10.0
    // returns a percentage value with 1 decimal point precision

  private inline def changeLabelColorFromThreshold[T](
      l: Label,
      inline low: Boolean,
      inline high: Boolean
  ): Unit =
    l.getStyle.fontColor =
      if low then Color.GREEN
      else if high then Color.RED
      else Color.ORANGE

  private def update(): Unit =
    val fpsCount = Gdx.graphics.getFramesPerSecond
    val cpuUsage = getProcessCpuLoad
    val ramInMB = memoryMXBean.getHeapMemoryUsage.getUsed / (1024 * 1024)

    this.fps.setText(s"$fpsCount FPS")
    this.cpu.setText(s"$cpuUsage%")
    this.cpu.setColor(Color.GREEN)
    this.ram.setText(s"$ramInMB MB used")
    this.sysInfo.setText(
      s"${this.runtimeMXBean.getVmName} ${this.runtimeMXBean.getVmVersion}"
    )

    this.changeLabelColorFromThreshold(
      this.fps,
      fpsCount > highFPSThreshold,
      fpsCount < lowFPSThreshold
    )
    this.changeLabelColorFromThreshold(
      this.cpu,
      cpuUsage < lowCPUThreshold,
      cpuUsage > highCPUThreshold
    )
    this.changeLabelColorFromThreshold(
      this.ram,
      ramInMB < lowRAMThreshold,
      ramInMB > highRAMThreshold
    )

  Timer
    .instance()
    .scheduleTask(
      () => update(),
      0f,
      updateFrequencyMs / 1000f
    )
