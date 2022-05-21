package fr.mesabloo.heavymachdefense.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import fr.mesabloo.heavymachdefense.MainGame

fun main() {
    val config = LwjglApplicationConfiguration()
    config.backgroundFPS = 30
    config.foregroundFPS = 60
    config.fullscreen = false

    config.width = 768
    config.height = 1024

    LwjglApplication(MainGame(), config)
}