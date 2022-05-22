package fr.mesabloo.heavymachdefense.systems.input.listener

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import fr.mesabloo.heavymachdefense.saves.GameSave
import fr.mesabloo.heavymachdefense.screens.MenuScreen

class MenuNewClickListener (private val screen: MenuScreen) : () -> Unit {
    private val inputListener = object : Input.TextInputListener {
        override fun input(text: String) {
            // TODO: create new save if possible (if there are slots remaining)
            //       however this is called on the rendering thread...

            val save =  GameSave()
            save.name = text

            screen.addSave(save)
        }

        override fun canceled() {

        }

    }

    override fun invoke() {
        Gdx.input.getTextInput(this.inputListener, "Enter your username:", "", "Username")
    }
}