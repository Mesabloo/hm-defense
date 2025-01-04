package io.github.mesabloo.hmdefense.app.utils

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.{Actor, Group}

/** A panel with a main view, and tabs to change the main view.
  *
  * @param skin
  * @param tabs
  * @param tabChanged
  */
final class TabbedPane(
    skin: Skin,
    tabs: Map[String, ? <: Actor] = Map(),
    tabChanged: String => Unit = name => ()
) extends Table:
  private val inner = Group()

  private val group = ButtonGroup[TextButton]()
  this.group.setMaxCheckCount(1)
  this.group.setMinCheckCount(1)
  this.group.setUncheckLast(true)

  private val tabsGroup = HorizontalGroup()
  this.tabsGroup.pad(2f, 6f, 2f, 6f).space(6f)
  for name <- tabs.keys do
    val button = TextButton(name, skin)
    button.addListener(
      new ChangeListener:
        override def changed(
            event: ChangeListener.ChangeEvent,
            actor: Actor
        ): Unit =
          val tab = actor.asInstanceOf[TextButton].getText.toString
          inner.clear()
          inner.addActor(tabs.getOrElse(tab, ???))
          tabChanged(tab)
    )

    this.group.add(button)
    this.tabsGroup.addActor(button)
  private val default =
    if this.tabsGroup.getChildren.size > 0 then Some(this.tabsGroup.getChild(0))
    else None

  this.add(this.tabsGroup).left().row()
  default.foreach(button =>
    val button_ = button.asInstanceOf[TextButton]
    button_.setChecked(true)
    this.inner.addActor(tabs.getOrElse(button_.getText.toString, ???))
  )
  this.add(inner).expand().fill().pad(0).grow()

  this.pack()
