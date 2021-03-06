package fr.mesabloo.heavymachdefense.ui.stage.dialog

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.listeners.stage.SaveAndExitStage
import fr.mesabloo.heavymachdefense.listeners.stage.SetBackgroundMusicVolume
import fr.mesabloo.heavymachdefense.listeners.stage.SetEffectsVolume
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.screens.StageScreen
import fr.mesabloo.heavymachdefense.timers.cellMiningTimer
import fr.mesabloo.heavymachdefense.ui.common.CloseButton
import ktx.graphics.color
import kotlin.reflect.KMutableProperty0

class SystemMenu(
    private val backgroundMusicVolume: KMutableProperty0<Float>,
    private val effectsVolume: KMutableProperty0<Float>,
    private val screen: StageScreen
) : Dialog("        ", WindowStyle().also {
    it.background = null
    it.titleFont = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_18]
}) {
    private enum class ButtonID {
        CLOSE,
        RESUME,
        SAVE_AND_EXIT,
        HELP,
        LEADER_BOARD
    }

    private val bg =
        Image(
            NinePatchDrawable(
                NinePatch(
                    stageAssetsManager.get(StageAssetsManager.Dialog.SYSTEM_MENU_BACKGROUND),
                    0,
                    0,
                    100,
                    60
                )
            )
        )

    init {
        this.isModal = true

        this.addActor(this.bg.also {
            it.width = 512f
        })

        this.buttonTable.also {
            it.zIndex = 500
            it.pad(8f).right().padRight(50f).padBottom(14f)
        }
        this.button(CloseButton(), ButtonID.CLOSE)

        this.contentTable.also { table ->
            table.zIndex = 500

            table.pad(8f, 8f, 0f, 8f)

            table.add(Group()).padBottom(70f).row()
            table.add(MenuButton(ButtonKind.RESUME).also {
                it.addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent?, x: Float, y: Float) {
                        this@SystemMenu.result(ButtonID.RESUME)
                    }
                })
            }).row()
            table.add(MenuButton(ButtonKind.SAVE).also {
                it.addListener(SaveAndExitStage(this.screen, this))
            }).row()
            table.add(MenuButton(ButtonKind.HELP)).row()
            table.add(MenuButton(ButtonKind.LEADER_BOARD)).padBottom(70f).row()
            table.add(Table().also {
                it.add(Image(stageAssetsManager.get(StageAssetsManager.Dialog.VOLUME_OPTIONS))).left()
                it.add(Table().also {
                    it.add(VolumeSlider().also {
                        it.addListener(SetBackgroundMusicVolume(this.backgroundMusicVolume))
                    }).center().row()
                    it.add(VolumeSlider().also {
                        it.addListener(SetEffectsVolume(this.effectsVolume))
                    }).center().row()
                }).fillX().expandX()
            }).row()
        }
    }

    override fun act(delta: Float) {
        super.act(delta)

        this.bg.height = this.height //+ 40f
    }

    override fun result(`object`: Any?) {
        super.result(`object`)

        when (`object`) {
            ButtonID.CLOSE, ButtonID.RESUME -> {
                this.hide()
            }
            else -> {
            }
        }
    }

    override fun show(stage: Stage): Dialog {
        cellMiningTimer.pause()

        val dialog = super.show(stage, null)
        dialog.width = 512f
        dialog.setPosition(stage.width / 2f - dialog.width / 2f, stage.height * 3f / 5f - dialog.height / 2f)

        return dialog
    }

    override fun hide() {
        cellMiningTimer.resume()

        super.hide(null)
    }
}

private enum class ButtonKind {
    RESUME,
    SAVE,
    HELP,
    LEADER_BOARD
}

private class MenuButton(kind: ButtonKind) : TextButton(when (kind) {
    ButtonKind.RESUME -> "Resume Game"
    ButtonKind.SAVE -> "Save & Exit Game"
    ButtonKind.HELP -> "Help"
    ButtonKind.LEADER_BOARD -> "Leader Board"
}, TextButtonStyle().also {
    it.down = TextureRegionDrawable(
        StageAssetsManager.Dialog.unsafeTexture(
            StageAssetsManager.Dialog.SYSTEM_MENU_BUTTONS,
            "selected"
        )
    )
    it.up = TextureRegionDrawable(
        StageAssetsManager.Dialog.unsafeTexture(
            StageAssetsManager.Dialog.SYSTEM_MENU_BUTTONS,
            "normal"
        )
    )
    it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_18]
    it.fontColor = color(0.478f, 1.000f, 0.933f)
})

private class VolumeSlider : Slider(0.0f, 1.0f, 0.05f, false, SliderStyle().also {
    it.background = TextureRegionDrawable(stageAssetsManager.get(StageAssetsManager.Dialog.SLIDER_RULE))
    it.knob = TextureRegionDrawable(stageAssetsManager.get(StageAssetsManager.Dialog.SLIDER_THUMB))
}) {
    init {
        this.value = this.maxValue
    }
}