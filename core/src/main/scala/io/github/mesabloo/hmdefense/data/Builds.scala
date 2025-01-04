package io.github.mesabloo.hmdefense.data

class MachineBuildInfo(val cellCost: Int, val time: Float, val maxUnits: Int)

object MachineBuilds extends (MachineKind => IArray[MachineBuildInfo]):
  private val tanker: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(200, 6f, 1),
    MachineBuildInfo(220, 5.5f, 1),
    MachineBuildInfo(240, 5f, 1),
    MachineBuildInfo(280, 4.5f, 1),
    MachineBuildInfo(330, 4f, 1),
    MachineBuildInfo(380, 3.5f, 1),
    MachineBuildInfo(430, 3f, 1),
    MachineBuildInfo(490, 2.5f, 1),
    MachineBuildInfo(550, 2f, 1),
    MachineBuildInfo(640, 2f, 1)
  )
  private val rifle: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(60, 3f, 5),
    MachineBuildInfo(80, 3f, 5),
    MachineBuildInfo(100, 3f, 5),
    MachineBuildInfo(120, 3f, 4),
    MachineBuildInfo(150, 3f, 4),
    MachineBuildInfo(200, 5f, 4),
    MachineBuildInfo(250, 5f, 4),
    MachineBuildInfo(320, 5f, 3),
    MachineBuildInfo(400, 5f, 3),
    MachineBuildInfo(500, 5f, 3)
  )
  private val missile: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(150, 10f, 2),
    MachineBuildInfo(160, 10f, 2),
    MachineBuildInfo(180, 10f, 2),
    MachineBuildInfo(200, 10f, 2),
    MachineBuildInfo(240, 10f, 2),
    MachineBuildInfo(280, 12f, 2),
    MachineBuildInfo(320, 12f, 2),
    MachineBuildInfo(380, 12f, 2),
    MachineBuildInfo(450, 12f, 2)
  )
  private val heavyMissile: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(300, 12f, 2),
    MachineBuildInfo(320, 12f, 2),
    MachineBuildInfo(350, 12f, 2),
    MachineBuildInfo(400, 12f, 2),
    MachineBuildInfo(450, 12f, 2),
    MachineBuildInfo(520, 15f, 2),
    MachineBuildInfo(600, 15f, 1),
    MachineBuildInfo(700, 15f, 1),
    MachineBuildInfo(800, 15f, 1),
    MachineBuildInfo(920, 15f, 1)
  )
  private val ion: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(800, 10f, 3),
    MachineBuildInfo(1000, 10f, 3),
    MachineBuildInfo(1200, 10f, 3),
    MachineBuildInfo(1400, 10f, 3),
    MachineBuildInfo(1700, 10f, 2),
    MachineBuildInfo(2000, 12f, 2),
    MachineBuildInfo(2400, 12f, 2),
    MachineBuildInfo(2800, 12f, 1),
    MachineBuildInfo(3200, 12f, 1),
    MachineBuildInfo(3600, 12f, 1)
  )
  private val hmg: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(210, 9f, 3),
    MachineBuildInfo(220, 9f, 3),
    MachineBuildInfo(240, 9f, 3),
    MachineBuildInfo(280, 9f, 3),
    MachineBuildInfo(320, 9f, 2),
    MachineBuildInfo(380, 9f, 2),
    MachineBuildInfo(450, 9f, 2),
    MachineBuildInfo(540, 9f, 2),
    MachineBuildInfo(670, 9f, 1),
    MachineBuildInfo(800, 9f, 1)
  )
  private val plasma: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(250, 8f, 3),
    MachineBuildInfo(260, 8f, 3),
    MachineBuildInfo(280, 8f, 3),
    MachineBuildInfo(300, 8f, 3),
    MachineBuildInfo(330, 8f, 2),
    MachineBuildInfo(370, 10f, 2),
    MachineBuildInfo(420, 10f, 2),
    MachineBuildInfo(480, 10f, 1),
    MachineBuildInfo(550, 10f, 1),
    MachineBuildInfo(640, 10f, 1)
  )
  private val shotgun: IArray[MachineBuildInfo] = IArray(
    MachineBuildInfo(130, 6f, 2),
    MachineBuildInfo(140, 6f, 2),
    MachineBuildInfo(150, 6f, 2),
    MachineBuildInfo(170, 6f, 2),
    MachineBuildInfo(200, 6f, 2),
    MachineBuildInfo(240, 8f, 2),
    MachineBuildInfo(290, 8f, 1),
    MachineBuildInfo(340, 8f, 1),
    MachineBuildInfo(400, 8f, 1),
    MachineBuildInfo(480, 8f, 1)
  )

  override def apply(kind: MachineKind): IArray[MachineBuildInfo] = kind match
    case MachineKind.Rifle        => this.rifle
    case MachineKind.Missile      => this.missile
    case MachineKind.HeavyMissile => this.heavyMissile
    case MachineKind.Ion          => this.ion
    case MachineKind.Hmg          => this.hmg
    case MachineKind.Plasma       => this.plasma
    case MachineKind.Shotgun      => this.shotgun
    case MachineKind.Tanker       => this.tanker

class TurretBuildInfo(val cellCost: Int, val time: Float)

object TurretBuilds extends (TurretKind => IArray[TurretBuildInfo]):
  private val rifle: IArray[TurretBuildInfo] = IArray(
    TurretBuildInfo(200, 12f),
    TurretBuildInfo(220, 12f),
    TurretBuildInfo(240, 12f),
    TurretBuildInfo(270, 12f),
    TurretBuildInfo(300, 12f)
  )
  private val missile: IArray[TurretBuildInfo] = IArray(
    TurretBuildInfo(300, 15f),
    TurretBuildInfo(320, 15f),
    TurretBuildInfo(340, 15f),
    TurretBuildInfo(370, 15f),
    TurretBuildInfo(400, 15f)
  )
  private val vulcan: IArray[TurretBuildInfo] = IArray(
    TurretBuildInfo(600, 12f),
    TurretBuildInfo(700, 12f),
    TurretBuildInfo(800, 12f),
    TurretBuildInfo(900, 12f),
    TurretBuildInfo(1000, 12f)
  )
  private val plasma: IArray[TurretBuildInfo] = IArray(
    TurretBuildInfo(400, 12f),
    TurretBuildInfo(430, 12f),
    TurretBuildInfo(470, 12f),
    TurretBuildInfo(520, 12f),
    TurretBuildInfo(580, 12f)
  )
  private val ion: IArray[TurretBuildInfo] = IArray(
    TurretBuildInfo(800, 12f),
    TurretBuildInfo(1000, 12f),
    TurretBuildInfo(1200, 12f),
    TurretBuildInfo(1500, 12f),
    TurretBuildInfo(1800, 12f)
  )
  private val laser: IArray[TurretBuildInfo] = IArray(
  )

  override def apply(kind: TurretKind): IArray[TurretBuildInfo] = kind match
    case TurretKind.Rifle   => this.rifle
    case TurretKind.Missile => this.missile
    case TurretKind.Vulcan  => this.vulcan
    case TurretKind.Plasma  => this.plasma
    case TurretKind.Ion     => this.ion
    case TurretKind.Laser   => this.laser
