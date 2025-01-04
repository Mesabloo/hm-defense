package io.github.mesabloo.hmdefense.assets.textures

/** An enum describing the paths from the assets folder root to all commonly
  * used textures.
  */
enum CommonTextures(val path: String):
  /** The bottom part of the loading screen.
    */
  case LoadingBottom extends CommonTextures("gfx/ui/loading/bottom.png")

  /** The top part of the loading screen.
    */
  case LoadingTop extends CommonTextures("gfx/ui/loading/top.png")

  /** The left part of the loading screen.
    */
  case LoadingLeft extends CommonTextures("gfx/ui/loading/left.png")

  /** The right part of the loading screen.
    */
  case LoadingRight extends CommonTextures("gfx/ui/loading/right.png")

  /** The central part of the loading screen.
    */
  case LoadingCenter extends CommonTextures("gfx/ui/loading/center.png")

inline given Conversion[CommonTextures, String] with
  override def apply(x: CommonTextures): String = x.path
