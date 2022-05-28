package fr.mesabloo.heavymachdefense

/**
 * `true` if the build should render debug information, else `false`.
 *
 * This is a value generated from the build configuration (see [BuildConfig]) created by the Gradle tool.
 */
const val DEBUG: Boolean = !BuildConfig.RELEASE

/**
 * Execute an action only when the constant [DEBUG] is `true`.
 */
inline fun ifDebug(crossinline action: () -> Unit) = if (DEBUG) action() else Unit