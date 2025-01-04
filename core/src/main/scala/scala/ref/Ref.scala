package scala.ref

class Ref[T](private var inner: T):
  def get: T = this.inner

  def set(x: T): Unit =
    this.inner = x
