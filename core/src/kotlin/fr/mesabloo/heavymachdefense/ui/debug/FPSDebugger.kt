package fr.mesabloo.heavymachdefense.ui.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import fr.mesabloo.heavymachdefense.managers.assets.DebugAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.debugAssetsManager
import ktx.graphics.color

class FPSDebugger : Label("0 FPS", LabelStyle().also {
    it.font = debugAssetsManager.font
    it.fontColor = color(1f, 0f, 0f)
    it.background = NinePatchDrawable(NinePatch(debugAssetsManager.get(DebugAssetsManager.BLACK_BG), 7, 7, 7, 7))
}) {
    override fun act(delta: Float) {
        super.act(delta)

        val javaMemoryUsedInMB = Gdx.app.javaHeap / (1024 * 1024)

        this.setText("${Gdx.graphics.framesPerSecond} FPS\n$javaMemoryUsedInMB MB used")

        this.width = this.glyphLayout.width + 14
        this.height = this.glyphLayout.height + 14
    }
}