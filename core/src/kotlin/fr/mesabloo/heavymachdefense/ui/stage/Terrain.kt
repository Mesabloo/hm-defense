package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

class Terrain : Group() {
    init {
        this.height = 2048f
        this.width = 512f

        val (bg1, bg2) = stageAssetsManager.background()!!

        this.addActor(Image(bg1).also {
            it.setPosition(0f, 0f)
        })
        this.addActor(Image(bg2).also {
            it.setPosition(0f, 1024f)
        })
    }
}