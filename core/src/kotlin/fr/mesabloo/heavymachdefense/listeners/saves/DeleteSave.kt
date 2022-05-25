package fr.mesabloo.heavymachdefense.listeners.saves

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.ui.dialogs

class DeleteSave(private val screen: SavesSelectionScreen) : ClickListener() {
    private val listener = ButtonClickListener { button ->
        when (button) {
            0 -> Unit // No
            1 -> { // Yes
                val saveIndex = this.screen.focusedIndex
                if (saveIndex != -1)
                    this.screen.removeSaveFromIndex(saveIndex)
            }
            else -> assert(false)
        }
    }

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (this.screen.focusedIndex != -1) {
            dialogs.newDialog(GDXButtonDialog::class.java)
                .setTitle("Heavy MACH: Defense")
                .setMessage("Do you really want to delete this save?")
                .setClickListener(this.listener)
                .addButton("No")
                .addButton("Yes")
                .build()
                .show()
        }
    }
}