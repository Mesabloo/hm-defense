package io.github.mesabloo.hmdefense.assets.atlases

import scala.CanEqual.derived
import scala.compiletime.error

/** An enum containing all common atlas assets.
  *
  * @param path
  *   The raw path from the root of the assets folder.
  */
enum CommonAtlases(val path: String):
  /** The buttons used throughout all the screens.
    */
  case Buttons extends CommonAtlases("gfx/ui/common_buttons.atlas")

inline given Conversion[CommonAtlases, String] with
  override def apply(x: CommonAtlases): String = x.path

/** An enum describing existing buttons.
  */
enum ButtonKind(private[atlases] val prefix: String) derives CanEqual:
  case Back extends ButtonKind("back")
  case Cancel extends ButtonKind("cancel")
  case Close extends ButtonKind("close")
  case Delete extends ButtonKind("delete")
  case Exit extends ButtonKind("exit")
  case New extends ButtonKind("new")
  case Next extends ButtonKind("next")
  case No extends ButtonKind("no")
  case Ok extends ButtonKind("ok")
  case Previous extends ButtonKind("previous")
  case Ranking extends ButtonKind("ranking")
  case Support extends ButtonKind("support")
  case Yes extends ButtonKind("yes")

/** An enum describing button alternative styles.
  */
enum ButtonVariant(private[atlases] val suffix: String) derives CanEqual:
  /** The button is enabled and not hovered/pressed.
    */
  case Normal extends ButtonVariant("")

  /** The button is enabled and either hovered or being pressed.
    */
  case Selected extends ButtonVariant("-selected")

  /** The button is disabled.
    */
  case Disabled extends ButtonVariant("-disabled")

/** Retrieves the string path of a certain kind and variant of a button, to
  * access the underlying sprite in the [[CommonAtlases.Buttons]] atlas.
  *
  * @param k
  *   The kind of button.
  * @param v
  *   The variant of the button.
  * @return
  *   A string representing the path in the [[CommonAtlases.Buttons]] atlas, or
  *   a compile-time error if the variant does not exist for the given button
  *   kind.
  */
inline def button(inline k: ButtonKind, inline v: ButtonVariant): String =
  inline (k, v) match
    case (_, ButtonVariant.Normal | ButtonVariant.Selected) |
        (ButtonKind.Ok, ButtonVariant.Disabled) =>
      k.prefix + v.suffix
    case _ => error("Failed to find given variant of given button.")
