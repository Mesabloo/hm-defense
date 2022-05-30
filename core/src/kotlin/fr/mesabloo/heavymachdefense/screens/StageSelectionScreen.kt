package fr.mesabloo.heavymachdefense.screens

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import fr.mesabloo.heavymachdefense.MainGame
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.listeners.stage_selection.BackToSaves
import fr.mesabloo.heavymachdefense.listeners.stage_selection.LoadStage
import fr.mesabloo.heavymachdefense.listeners.stage_selection.SelectOrLoadStage
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.ui.common.BackButton
import fr.mesabloo.heavymachdefense.ui.common.OkButton
import fr.mesabloo.heavymachdefense.ui.common.SupportButton
import fr.mesabloo.heavymachdefense.ui.stage_selection.Background
import fr.mesabloo.heavymachdefense.ui.stage_selection.Foreground
import fr.mesabloo.heavymachdefense.ui.stage_selection.StageList
import fr.mesabloo.heavymachdefense.world.UI_WIDTH
import ktx.actors.setScrollFocus

class StageSelectionScreen(
    game: MainGame,
    val save: GameSave,
    val saveIndex: Int,
    isLoading: Boolean = false
) : AbstractScreen(game, isLoading) {
    lateinit var scrollPane: StageList
        private set

    override fun show() {
        super.show()

        if (this.isLoading)
            return

        this.background.addActor(Background().also {
            it.setPosition(0f, 0f)
        })

        this.scrollPane = StageList(save.lastStageCompleted + 1).also {
            (it.getChild(0) as Table)
                .cells
                .forEachIndexed { index, cell ->
                    cell.actor.addListener(SelectOrLoadStage(this@StageSelectionScreen, index))
                }

            val table = it.getChild(0) as Table
            val cellHeight = table.cells[0].prefHeight
            val offsetY = table.prefHeight - (it.selected.toFloat() - 2) * cellHeight

            it.scrollTo(0f, offsetY, it.width, it.height)
            //it.layout()
        }
        this.background.addActor(Table().also {
            it.setPosition(128f, 140f)
            it.setSize(512f, 664f)
            it.touchable = Touchable.enabled

            it.add(scrollPane).expand().fill()
        })

        this.background.addActor(Foreground().also {
            it.setPosition(0f, 0f)

            it.touchable = Touchable.disabled
        })

        this.background.addActor(BackButton().also {
            it.setPosition(UI_WIDTH * 1f / 4f - it.width / 2f + 32f, 30f)
            it.addListener(BackToSaves(this, it))
        })
        this.background.addActor(SupportButton().also {
            it.setPosition(UI_WIDTH * 2f / 4f - it.width / 2f, 30f)
            //it.addListener(DeleteSave(this))
        })
        this.background.addActor(OkButton().also {
            it.setPosition(UI_WIDTH * 3f / 4f - it.width / 2f - 32f, 30f)
            it.addListener(LoadStage(this))
        })

        if (!this.scrollPane.hasScrollFocus()) {
            this.scrollPane.setScrollFocus(true)
        }
    }

    override fun dispose() {
        super.dispose()

        levelSelectionAssetsManager.dispose()
    }
}