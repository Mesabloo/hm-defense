package io.github.mesabloo.hmdefense.data

enum UpgradeKind extends Enum[UpgradeKind] with Serializable derives CanEqual:
  case BaseCannon extends UpgradeKind
  case BaseDefense extends UpgradeKind
  case BuildTime extends UpgradeKind
  case CellStorage extends UpgradeKind
  case CellResearch extends UpgradeKind
  case CreditResearch extends UpgradeKind

class BaseCannonUpgrade(
    val cost: Int,
    val attack: Int,
    val reload: Float,
    val shellCount: Int,
    val shotDelay: Float,
    val bulletSpeed: Float,
    val spread: Float
)

class BaseDefenseUpgrade(
    val cost: Int,
    val defense: Int
)

class BuildTimeUpgrade(
    val cost: Int,
    val multiplier: Float
)

class CellStorageUpgrade(
    val cost: Int,
    val storage: Int
)

class CellResearchUpgrade(
    val cost: Int,
    val multiplier: Float
)

class CreditResearchUpgrade(
    val cost: Int,
    val multiplier: Float
)

object Upgrades:
  val baseCannon: IArray[BaseCannonUpgrade] = IArray(
    BaseCannonUpgrade(600, 0, 2.5f, 10, 0.16f, 1f, 1.0f),
    BaseCannonUpgrade(1200, 0, 2.4f, 12, 0.15f, 1f, 0.95f),
    BaseCannonUpgrade(2400, 0, 2.3f, 14, 0.14f, 1f, 0.9f),
    BaseCannonUpgrade(4800, 0, 2.2f, 16, 0.13f, 1f, 0.85f),
    BaseCannonUpgrade(9600, 0, 2.1f, 18, 0.12f, 1f, 0.8f),
    BaseCannonUpgrade(19200, 0, 2f, 20, 0.11f, 1f, 0.75f),
    BaseCannonUpgrade(0, 0, 1.9, 24, 0.1f, 1f, 0.7f)
  )
  val baseDefense: IArray[BaseDefenseUpgrade] = IArray(
    BaseDefenseUpgrade(600, 10000),
    BaseDefenseUpgrade(1200, 15000),
    BaseDefenseUpgrade(2400, 20000),
    BaseDefenseUpgrade(4800, 30000),
    BaseDefenseUpgrade(9600, 50000),
    BaseDefenseUpgrade(19200, 70000),
    BaseDefenseUpgrade(0, 100000)
  )
  val buildTime: IArray[BuildTimeUpgrade] = IArray(
    BuildTimeUpgrade(700, 1f),
    BuildTimeUpgrade(1400, 0.96f),
    BuildTimeUpgrade(2800, 0.92f),
    BuildTimeUpgrade(5600, 0.88f),
    BuildTimeUpgrade(11200, 0.84f),
    BuildTimeUpgrade(22400, 0.8f),
    BuildTimeUpgrade(44800, 0.76f),
    BuildTimeUpgrade(89600, 0.72f),
    BuildTimeUpgrade(179200, 0.68f),
    BuildTimeUpgrade(0, 0.64f)
  )
  val cellStorage: IArray[CellStorageUpgrade] = IArray(
    CellStorageUpgrade(700, 500),
    CellStorageUpgrade(1400, 700),
    CellStorageUpgrade(2800, 1000),
    CellStorageUpgrade(5600, 1400),
    CellStorageUpgrade(11200, 1800),
    CellStorageUpgrade(22400, 2300),
    CellStorageUpgrade(44800, 2900),
    CellStorageUpgrade(89600, 3400),
    CellStorageUpgrade(179200, 4000),
    CellStorageUpgrade(0, 4700)
  )
  val cellResearch: IArray[CellResearchUpgrade] = IArray(
    CellResearchUpgrade(800, 1f),
    CellResearchUpgrade(1600, 1.1f),
    CellResearchUpgrade(3200, 1.2f),
    CellResearchUpgrade(6400, 1.3f),
    CellResearchUpgrade(12800, 1.4f),
    CellResearchUpgrade(25600, 1.5f),
    CellResearchUpgrade(51200, 1.6f),
    CellResearchUpgrade(102400, 1.8f),
    CellResearchUpgrade(204800, 2f),
    CellResearchUpgrade(0, 2.2f)
  )
  val creditResearch: IArray[CreditResearchUpgrade] = IArray(
    CreditResearchUpgrade(500, 1f),
    CreditResearchUpgrade(1000, 1.2f),
    CreditResearchUpgrade(2000, 1.4f),
    CreditResearchUpgrade(4000, 1.6f),
    CreditResearchUpgrade(8000, 1.8f),
    CreditResearchUpgrade(16000, 2f),
    CreditResearchUpgrade(32000, 2.2f),
    CreditResearchUpgrade(64000, 2.4f),
    CreditResearchUpgrade(128000, 2.4f),
    CreditResearchUpgrade(0, 3f)
  )
