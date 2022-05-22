package fr.mesabloo.heavymachdefense.log

import com.badlogic.gdx.ApplicationLogger
import java.util.*

/**
 * A colored Gdx logger which logs to [System.err].
 *
 *  * Log messages are colored in Blue
 *  * Debug messages are colored in Magenta
 *  * Error messages are colored in Red
 *
 * Note: This uses ANSI color codes, which should now be supported on all major
 * modern OSes (Linux, macOS and Windows).
 * In case your terminal does not support ANSI color codes, the output should contain
 * junk characters (e.g. `☐[32m`).
 */
class ColoredLogger : ApplicationLogger {
    override fun log(name: String, msg: String) {
        System.err.println("\u001B[32m[${Date()} - $name -  LOG \u001B[0m] $msg")
    }

    override fun log(name: String, msg: String, throwable: Throwable) {
        System.err.println("\u001B[32m[${Date()} - $name -  LOG \u001B[0m] $msg\n$throwable")
    }

    override fun error(name: String, msg: String) {
        System.err.println("\u001B[31m[${Date()} - $name - ERROR\u001B[0m] $msg")
    }

    override fun error(name: String, msg: String, throwable: Throwable) {
        System.err.println("\u001B[31m[${Date()} - $name - ERROR\u001B[0m] $msg\n$throwable")
    }

    override fun debug(name: String, msg: String) {
        System.err.println("\u001B[36m[${Date()} - $name - DEBUG\u001B[0m] $msg")
    }

    override fun debug(name: String, msg: String, throwable: Throwable) {
        System.err.println("\u001B[36m[${Date()} - $name - DEBUG\u001B[0m] $msg\n$throwable")
    }
}