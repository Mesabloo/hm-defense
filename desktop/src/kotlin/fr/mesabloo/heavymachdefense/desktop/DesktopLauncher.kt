package fr.mesabloo.heavymachdefense.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import fr.mesabloo.heavymachdefense.MainGame

fun main() {
    var config = LwjglApplicationConfiguration()
    config.backgroundFPS = 30
    config.foregroundFPS = 60


    LwjglApplication(MainGame(), config)
}