package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.ui.stage.dialog.SystemMenu

class ShowSystemMenu(private val menu: SystemMenu, private val stage: Stage) : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        this.menu.zIndex = 500000000
        this.menu.show(this.stage)
    }
}