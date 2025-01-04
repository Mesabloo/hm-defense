package io.github.mesabloo.hmdefense.app.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.{Logger, ScreenUtils}
import com.badlogic.gdx.{Game, Gdx, InputMultiplexer, ScreenAdapter}

private class MachineTester extends Group:
  private val renderer = ShapeRenderer()

  override def draw(batch: Batch, parentAlpha: Float): Unit =
    batch.end()

    this.renderer.setProjectionMatrix(batch.getProjectionMatrix)
    this.renderer.setTransformMatrix(batch.getTransformMatrix)
    this.renderer.begin(ShapeType.Line)

    this.renderer.end()

    batch.begin()
    super.draw(batch, parentAlpha)

private class TesterScreen2 extends ScreenAdapter:
  private val skin = new Skin(
    Gdx.files.internal("utils/model-tester/uiskin.json")
  )

  private val stage = Stage(ScreenViewport())
  private val machinePlayground = MachineTester()
  private val tabs =
    TabbedPane(
      skin,
      Map("Machine" -> this.machinePlayground, "Turret" -> Group()),
      tab => ()
    )

  this.stage.addActor(this.tabs)

  private val inputMux = InputMultiplexer()
  inputMux.addProcessor(stage)
  Gdx.input.setInputProcessor(inputMux)

  private def resizeUI(): Unit =
    this.tabs.setSize(this.stage.getWidth, this.stage.getHeight)

  override def render(delta: Float): Unit =
    ScreenUtils.clear(Color.BLACK)
    super.render(delta)

    this.stage.act(delta)
    this.stage.draw()

  override def resize(width: Int, height: Int): Unit =
    super.resize(width, height)
    this.stage.getViewport.update(width, height, true)
    this.resizeUI()

private class AnimationTester extends Game:
  override def create(): Unit =
    Gdx.app.setLogLevel(Logger.DEBUG)

    this.setScreen(TesterScreen2())
