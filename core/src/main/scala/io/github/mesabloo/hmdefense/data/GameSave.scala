package io.github.mesabloo.hmdefense.data

import io.github.mesabloo.hmdefense
import io.github.mesabloo.hmdefense.data

import java.util.Date
import scala.annotation.unused
import scala.collection.mutable

/** An exception for invalid [[GameSave]] objects.
  *
  * @param reason
  *   Why is the save invalid?
  */
class InvalidSaveException private[data] (
    private val reason: String
) extends Throwable:
  override def getMessage: String = s"Game save is invalid: $reason"

/** A few compile-time constants for [[GameSave]]s. All of them are private to
  * this module in order to avoid leaking them outside of this module.
  */
private case object SaveConstants:
  /** The default last unlocked level index. */
  private[data] inline def DEFAULT_LAST_UNLOCKED_LEVEL = 1

  /** The default credits amount. */
  private[data] inline def DEFAULT_CREDITS = 0L

  /** The default machine upgrade levels. */
  private[data] inline def DEFAULT_MACHINE_UPGRADES =
    mutable
      .HashMap((MachineKind.Rifle, 1))
      .withDefault((kind: MachineKind) => 0)

  private[data] inline def DEFAULT_TURRET_UPGRADES =
    mutable.HashMap().withDefault((kind: TurretKind) => 0)

  private[data] inline def DEFAULT_UPGRADES =
    mutable.HashMap().withDefault((kind: UpgradeKind) => 1)

  private[data] inline def DEFAULT_BUILD_SLOTS =
    mutable.ArrayBuffer[MachineKind | TurretKind](MachineKind.Rifle)

/** All the kinds of machines which are present in the game. */
enum MachineKind(private val id: String)
    extends Enum[MachineKind]
    with Serializable derives CanEqual:
  case Rifle extends MachineKind("RifleGun")
  case Missile extends MachineKind("Missile")
  case HeavyMissile extends MachineKind("HeavyMissile")
  case Ion extends MachineKind("Ion rifle")
  case Hmg extends MachineKind("HMG")
  case Plasma extends MachineKind("PlasmaGun")
  case Shotgun extends MachineKind("Shotgun")
  case Tanker extends MachineKind("Tanker")

  /** @return
    *   The name of the machine.
    */
  def getName: String = this.id

/** All the kinds of turrets which are present in the game. */
enum TurretKind(private val id: String)
    extends Enum[TurretKind]
    with Serializable derives CanEqual:
  case Rifle extends TurretKind("RifleTurret")
  case Missile extends TurretKind("MissileTurret")
  case Vulcan extends TurretKind("Vulcan")
  case Plasma extends TurretKind("PlasmaTurret")
  case Ion extends TurretKind("IonTurret")
  case Laser extends TurretKind("LaserTurret")

  /** @return
    *   The name of the turret.
    */
  def getName: String = this.id

/** @param name
  * @param creationDate
  * @param lastAccessedDate
  * @param lastUnlockedLevel
  * @param credits
  * @param machineUpgrades
  * @param turretUpgrades
  * @param upgrades
  */
class GameSave(
    val name: String,
    val creationDate: Date,
    var lastAccessedDate: Date,
    var lastUnlockedLevel: Int = SaveConstants.DEFAULT_LAST_UNLOCKED_LEVEL,
    var credits: Long = SaveConstants.DEFAULT_CREDITS,
    val machineUpgrades: mutable.Map[MachineKind, Int] =
      SaveConstants.DEFAULT_MACHINE_UPGRADES,
    val turretUpgrades: mutable.Map[TurretKind, Int] =
      SaveConstants.DEFAULT_TURRET_UPGRADES,
    val upgrades: mutable.Map[UpgradeKind, Int] =
      SaveConstants.DEFAULT_UPGRADES,
    val buildSlots: mutable.Buffer[MachineKind | TurretKind] =
      SaveConstants.DEFAULT_BUILD_SLOTS
) extends Serializable:
  /** An auxiliary constructor to allow constructing default objects from Java.
    * This is marked unused because it effectively isn't in our Scala code.
    */
  @unused @inline
  private[data] def this(name: String, creationDate: Date) =
    // It's a shame that we have to repeat the default values for each parameter...
    this(
      name,
      creationDate,
      creationDate
    )

  /** @return
    *   `true` if the game save is valid according to our own (artificial)
    *   rules.
    * @throws InvalidSaveException
    *   When the save is invalid/inconsistent according to the game rules.
    */
  @throws[InvalidSaveException]
  def checkValid(): Unit =
    if this.lastUnlockedLevel < 1 then
      throw InvalidSaveException(
        s"`this.lastUnlockedLevel` must be included in 1..80; found ${this.lastUnlockedLevel}."
      )
    if this.lastUnlockedLevel > 80 then
      throw InvalidSaveException(
        s"`this.lastUnlockedLevel` must be included in 1..80; found ${this.lastUnlockedLevel}."
      )
    if this.credits < 0 then
      throw InvalidSaveException(
        s"`this.credits` must be a strictly positive integer; found ${this.credits}"
      )

    if this.buildSlots.size > 7 || this.buildSlots.isEmpty then
      throw InvalidSaveException(
        s"There must be between 1 and 7 build slots."
      )
    for kind <- this.buildSlots do
      kind match
        case kind: MachineKind =>
          if this.machineUpgrades(kind) == 0 then
            throw InvalidSaveException(
              s"`this.buildSlots` contains machines ($kind) which have not been unlocked."
            )
        case kind: TurretKind =>
          if this.turretUpgrades(kind) == 0 then
            throw InvalidSaveException(
              s"`this.buildSlots` contains turrets ($kind) which have not been unlocked."
            )

    for upg <- UpgradeKind.values do
      val maxLvl = upg match
        case hmdefense.data.UpgradeKind.BaseCannon  => Upgrades.baseCannon.size
        case hmdefense.data.UpgradeKind.BaseDefense => Upgrades.baseDefense.size
        case hmdefense.data.UpgradeKind.BuildTime   => Upgrades.buildTime.size
        case hmdefense.data.UpgradeKind.CellStorage => Upgrades.cellStorage.size
        case hmdefense.data.UpgradeKind.CellResearch =>
          Upgrades.cellResearch.size
        case hmdefense.data.UpgradeKind.CreditResearch =>
          Upgrades.creditResearch.size
      if this.upgrades(upg) > maxLvl || this.upgrades(upg) <= 0 then
        throw InvalidSaveException(
          s"Upgrade $upg must have its level included in 1..$maxLvl"
        )
