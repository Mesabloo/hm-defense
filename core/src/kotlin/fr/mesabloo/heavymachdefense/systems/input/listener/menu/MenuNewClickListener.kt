package fr.mesabloo.heavymachdefense.systems.input.listener.menu

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener
import fr.mesabloo.heavymachdefense.saves.GameSave
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import fr.mesabloo.heavymachdefense.systems.input.dialogs.dialogs

class MenuNewClickListener (private val screen: MenuScreen) : () -> Unit {
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

    override fun invoke() {
        if (screen.numberOfSaves < 5) {
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