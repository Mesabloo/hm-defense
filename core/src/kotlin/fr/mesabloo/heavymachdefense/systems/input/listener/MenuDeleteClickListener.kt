package fr.mesabloo.heavymachdefense.systems.input.listener

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import fr.mesabloo.heavymachdefense.systems.input.dialogs.dialogs

class MenuDeleteClickListener(private val screen: MenuScreen) : () -> Unit {
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

    override fun invoke() {
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