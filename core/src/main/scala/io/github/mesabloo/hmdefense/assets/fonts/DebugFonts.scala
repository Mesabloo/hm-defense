package io.github.mesabloo.hmdefense.assets.fonts

enum DebugFonts(val path: String):
  case FPSFont extends DebugFonts("fonts/debug.fnt")
  case CPUFont extends DebugFonts("fonts/debug.fnt")
  case RAMFont extends DebugFonts("fonts/debug.fnt")
  case SystemInfoFont extends DebugFonts("fonts/debug.fnt")

inline given Conversion[DebugFonts, String] with
  override def apply(x: DebugFonts): String = x.path
