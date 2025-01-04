package io.github.mesabloo.hmdefense.logging

import com.badlogic.gdx.ApplicationLogger

import java.util.Date
import scala.io.AnsiColor.*

/** A GDX logger outputting to [[Console.err]] with colors:
  *
  *   - Blue for logs;
  *   - Red for errors;
  *   - Magenta for debugs.
  */
object ColoredLogger extends ApplicationLogger:
  final override def log(tag: String, message: String): Unit =
    Console.err.println(s"$BOLD$BLUE${Date()} ─ $tag [i]$RESET $message")

  final override def log(
      tag: String,
      message: String,
      exception: Throwable
  ): Unit =
    this.log(tag, s"$message\n$exception")

  final override def error(tag: String, message: String): Unit =
    Console.err.println(s"$BOLD$RED${Date()} ─ $tag [!]$RESET $message")

  final override def error(
      tag: String,
      message: String,
      exception: Throwable
  ): Unit =
    this.log(tag, s"$message\n$exception")

  final override def debug(tag: String, message: String): Unit =
    Console.err.println(s"$BOLD$MAGENTA${Date()} ─ $tag [?]$RESET $message")

  final override def debug(
      tag: String,
      message: String,
      exception: Throwable
  ): Unit =
    this.debug(tag, s"$message\n$exception")
