package io.github.mesabloo.hmdefense.assets.textures

/** All the textures used in the [[StartupScreen]].
  *
  * @param path
  *   The raw path from the root of the assets folder.
  */
enum StartupTextures(val path: String):
  /** The background texture, which covers the full stage.
    */
  case Background extends StartupTextures("gfx/startup/title.jpg")

  /** The small `Tap to start` flashing image in the center of the stage.
    */
  case TapToStart extends StartupTextures("gfx/startup/tap_to_start.png")

inline given Conversion[StartupTextures, String] with
  override def apply(x: StartupTextures): String = x.path
