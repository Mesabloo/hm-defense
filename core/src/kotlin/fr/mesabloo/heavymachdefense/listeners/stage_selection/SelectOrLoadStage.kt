package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.listeners.stage_selection

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.screens.StageSelectionScreen

class SelectOrLoadStage(private val screen: StageSelectionScreen, private val index: Int) : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        Gdx.app.debug(this.javaClass.simpleName, "Clicked on item number $index")

        if (index > this.screen.save.lastStageCompleted)
            return

        if (this.screen.scrollPane.selected == index) {
            TODO("switch to next screen")
        } else {
            this.screen.scrollPane.selected = index
        }
    }
}