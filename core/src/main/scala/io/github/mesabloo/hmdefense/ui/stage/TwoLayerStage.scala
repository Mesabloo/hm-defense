package io.github.mesabloo.hmdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.{Actor, Group, Stage, Touchable}
import com.badlogic.gdx.utils
import io.github.mesabloo.hmdefense.config.whenDebug

/** A simple enum to describe which layer to add actors to in a
  * [[TwoLayerStage]].
  */
enum DefaultLayer derives CanEqual:
  /** The foreground layer.
    */
  case Foreground

  /** The background layer.
    */
  case Background

/** An overloaded version of a [[Stage]] where there are two different rendering
  * layers:
  *
  *   - The foreground layer, which will show anything over the background
  *     layer;
  *   - The background layer.
  *
  * @param whichLayer
  *   The default layer to add actors to.
  */
abstract class TwoLayerStage(whichLayer: DefaultLayer = DefaultLayer.Background)
    extends Stage:
  whenDebug:
    this.setDebugAll(true)

  /** This is the foreground layer, as a [[Group]] of [[Actor]]s.
    *
    * Access is `protected` to allow direct access in subclasses.
    */
  protected final val foreground: Group = Group()

  /** This is the background layer, as a [[Group]] of [[Actor]]s.
    *
    * Access is `protected` to allow direct access in subclasses.
    */
  protected final val background: Group = Group()

  /** The layer to which to add [[Actor]]s to in [[addActor]].
    *
    * It is precomputed, so that we do not pay the price of computing it
    * everytime we add a new actor to the stage.
    */
  private final val defaultLayer = whichLayer match
    case DefaultLayer.Foreground => this.foreground
    case DefaultLayer.Background => this.background

  /** Adds an actor to the default layer, instead of to the stage directly.
    *
    * @param actor
    *   The new actor to add to the default layer.
    */
  final override def addActor(actor: Actor): Unit =
    this.defaultLayer.addActor(actor)

  /** Removes all actors of the default layer.
    */
  final override def clear(): Unit = this.defaultLayer.clear(true)

  /** Returns all the actors inside the default layer.
    * @return
    *   The actors of the default layer.
    */
  final override def getActors: utils.Array[Actor] =
    this.defaultLayer.getChildren

  //////////////////////////////////////////////////////////

  super.addActor(background)
  super.addActor(foreground)

  this.foreground.setTouchable(Touchable.childrenOnly)
  this.background.setTouchable(Touchable.childrenOnly)
