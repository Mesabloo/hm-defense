package io.github.mesabloo.hmdefense.assets.textures

import io.github.mesabloo.hmdefense.data.backgroundIndex

enum GameTextures(val path: String):
  case Background(level: Int, firstPart: Boolean, alt: Boolean, small: Boolean)
      extends GameTextures({
        assert(!(alt && small))

        val lvl = "%02d".format(backgroundIndex(level))
        val part = if firstPart then "01" else "02"
        s"gfx/game/background/$lvl/$part${
            if alt then "-alt.jpg" else if small then "-small.png" else ".jpg"
          }"
      })
  case MapBuildQueue extends GameTextures("gfx/game/map_build-queue.png")
  case MachSlots extends GameTextures("gfx/game/mach-slots.png")
  case RadarBorder extends GameTextures("gfx/game/radar/border.png")
  case BuildSlotForeground
      extends GameTextures("gfx/game/build-slot/foreground.png")
  case BuildSlotBackground
      extends GameTextures("gfx/game/build-slot/background.png")
  case BuildSlotCover extends GameTextures("gfx/game/build-slot/cover.png")
  case BuildSlotIndicatorBlue
      extends GameTextures("gfx/game/build-slot/indicator-blue.png")
  case BuildSlotIndicatorRed
      extends GameTextures("gfx/game/build-slot/indicator-red.png")

inline given Conversion[GameTextures, String] with
  override def apply(x: GameTextures): String = x.path
