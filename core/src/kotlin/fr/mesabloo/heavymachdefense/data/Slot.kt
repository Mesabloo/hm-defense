package fr.mesabloo.heavymachdefense.data

import kotlinx.serialization.Serializable

@Serializable
abstract class Slot

@Serializable
data class MachineSlot(val kind: MachineKind) : Slot()

@Serializable
data class TurretSlot(val kind: TurretKind) : Slot()
