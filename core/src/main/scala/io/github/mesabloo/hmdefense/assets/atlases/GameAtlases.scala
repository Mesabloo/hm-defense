package io.github.mesabloo.hmdefense.assets.atlases

enum GameAtlases(val path: String):
  case RadarMarks extends GameAtlases("gfx/game/radar/marks.atlas")

inline given Conversion[GameAtlases, String] with
  override def apply(x: GameAtlases): String = x.path
