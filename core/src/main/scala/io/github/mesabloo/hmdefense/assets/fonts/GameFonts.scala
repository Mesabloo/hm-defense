package io.github.mesabloo.hmdefense.assets.fonts

enum GameFonts(val path: String):
  case CellCounter extends GameFonts("fonts/stage.fnt")
  case CellTotal extends GameFonts("fonts/level.fnt")
  case BuildSlotName extends GameFonts("fonts/trebuchet_ms_bd_11_white.fnt")

inline given Conversion[GameFonts, String] with
  override def apply(x: GameFonts): String = x.path
