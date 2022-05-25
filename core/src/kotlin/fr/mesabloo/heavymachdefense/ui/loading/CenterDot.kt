package fr.mesabloo.heavymachdefense.ui.loading

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.LoadingAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.loadingAssetsManager
import ktx.math.div
import ktx.math.minus
import ktx.math.plus
import ktx.math.vec2

class CenterDot : Image(loadingAssetsManager.texture(LoadingAssetsManager.LOADING_CENTER)) {

    override fun setScale(scaleX: Float, scaleY: Float) {
        val oldSize = vec2(this.width * this.scaleX, this.height * this.scaleY)
        super.setScale(scaleX, scaleY)
        val newSize = vec2(this.width * scaleX, this.height * scaleY)

        val oldPos = vec2(this.x, this.y)
        val newPos = oldPos + (oldSize - newSize) / 2f

        this.x = newPos.x
        this.y = newPos.y
    }
}