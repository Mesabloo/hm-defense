package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.ui.stage_selection

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.data.getBackgroundForLevel
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.LevelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import ktx.graphics.color

class StageList(private val last: Int) : ScrollPane(Table()) {
    private val normalBackground =
        levelSelectionAssetsManager.button(LevelSelectionAssetsManager.Companion.ButtonState.NORMAL)
    private val disabledBackground =
        levelSelectionAssetsManager.button(LevelSelectionAssetsManager.Companion.ButtonState.DISABLED)
    private val selectedForeground =
        levelSelectionAssetsManager.button(LevelSelectionAssetsManager.Companion.ButtonState.SELECTED)

    private companion object {
        const val BACKGROUND_PADDING = 4f
    }

    var selected: Int = this.last - 1
        set(value) {
            assert(value in 0..79)
            field = value

            (this.getChild(0) as Table).children
                .forEachIndexed { index, actor ->
                    (actor as Table).background = TextureRegionDrawable(
                        if (index == value) selectedForeground
                        else if (index < last) normalBackground
                        else disabledBackground
                    )
                }
        }

    init {
        this.touchable = Touchable.enabled
        this.setScrollingDisabled(true, false)
        this.setSmoothScrolling(true)

        val table = this.getChild(0) as Table
        table.touchable = Touchable.enabled
        for (index in 0..79) {
            table.add(createItem(index, last))
                .center().fill().expand()
                .row()
        }
    }

    private fun createItem(index: Int, last: Int): Table {
        val stageNumber = Label("Stage ${index + 1}", Label.LabelStyle().also {
            it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_28_BLUE]
            it.fontColor = if (index < last) color(0.478f, 1.000f, 0.933f) else color(0f, 0f, 0f)
        })
        stageNumber.setAlignment(Align.center)
        stageNumber.touchable = Touchable.disabled

        val backgrounds = levelSelectionAssetsManager.stageBackground(getBackgroundForLevel(index + 1))

        val table = Table()
        table.touchable = Touchable.enabled

        table.pad(BACKGROUND_PADDING, 34f, BACKGROUND_PADDING, 34f)

        table.add(stageNumber)
            .center().fillX().expandX()
        table.add(Image(backgrounds.first).also { it.touchable = Touchable.disabled })
            .fillY().expandY()
            .padTop(BACKGROUND_PADDING).padBottom(BACKGROUND_PADDING)
        table.add(Image(backgrounds.second).also { it.touchable = Touchable.disabled })
            .fillY().expandY()
            .padTop(BACKGROUND_PADDING).padBottom(BACKGROUND_PADDING)

        table.background = TextureRegionDrawable(
            if (index == this.last - 1) selectedForeground
            else if (index < last) normalBackground
            else disabledBackground
        )

        return table
    }
}