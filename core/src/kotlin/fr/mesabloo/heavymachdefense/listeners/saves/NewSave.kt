package fr.mesabloo.heavymachdefense.listeners.saves

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener
import fr.mesabloo.heavymachdefense.data.GameSave
import fr.mesabloo.heavymachdefense.screens.SavesSelectionScreen
import fr.mesabloo.heavymachdefense.ui.dialogs

class NewSave(private val screen: SavesSelectionScreen) : ClickListener() {
    private val inputListener = object : TextPromptListener {
        override fun confirm(text: String) {
            // TODO: create new save if possible (if there are slots remaining)
            //       however this is called on the rendering thread...

            val save = GameSave()
            save.name = text

            screen.addSave(save)
        }

        override fun cancel() {

        }
    }

    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        if (this.screen.numberOfSaves < 5) {
            dialogs.newDialog(GDXTextPrompt::class.java)
                .setTitle("Heavy MACH: Defense")
                .setMessage("Enter your username")
                .setTextPromptListener(this.inputListener)
                .build()
                .show()
        } else {
            dialogs.newDialog(GDXButtonDialog::class.java)
                .setTitle("Heavy MACH: Defense")
                .setMessage("Not enough slots to create a new save")
                .addButton("Ok")
                .build()
                .show()
        }
    }
}