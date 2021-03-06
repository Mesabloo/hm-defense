package fr.mesabloo.heavymachdefense.ui

/**
 * An enumeration to hold every kind of buttons which can be created from the `gfx/ui/buttons/common.atlas` atlas file.
 * Each entry holds its low-level name, and may be prepended to either `-selected` or `-disabled` for alternative styles.
 */
enum class Button(val str: String) {
    BACK("back"),
    CANCEL("cancel"),
    CLOSE("close"),
    DELETE("delete"),
    EXIT("exit"),
    NEW("new"),
    NEXT("next"),
    NO("no"),
    OK("ok"),
    PREVIOUS("previous"),
    RANKING("ranking"),
    SUPPORT("support"),
    YES("yes")
}