package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.math.MathUtils.ceil
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import kotlin.math.floor
import kotlin.math.min


private enum class BorderKind {
    TOP, BOTTOM, LEFT, RIGHT
}

private class Border(val border: BorderKind, isHorizontal: Boolean, maxHeight: Float) : Group() {
    init {
        fun mkImage(paddingToRemove: Float = 0f) =
            Image(stageAssetsManager.get(StageAssetsManager.UI.RADAR_BORDER)).also {
                it.setOrigin(Align.center)
                it.width = 4f
                it.height = floor(16f - paddingToRemove)

                if (isHorizontal) {
                    it.rotation = 90f
                }
            }

        val nbBorder = if (isHorizontal) 4 else ceil(maxHeight / 16f)
        val extraTopPadding = if (isHorizontal) 0f else nbBorder.toFloat() * 16f - maxHeight + 1f
        val paddingPerBorder = if (isHorizontal) 0f else ceil(extraTopPadding / nbBorder).toFloat()

        if (isHorizontal) {
            this.width = 64f
            this.height = 4f
        } else {
            this.width = 4f
            this.height = maxHeight + this.width
        }

        var totalY = 0f

        for (i in 0 until nbBorder) {
            if (isHorizontal) {
                this.addActor(mkImage(paddingPerBorder).also {
                    it.setPosition(i.toFloat() * 16f - it.width, -it.height + it.width)
                })
            } else {
                this.addActor(mkImage(paddingPerBorder).also {
                    it.setPosition(0f, i.toFloat() * 16f - if (i == 0) 0f else paddingPerBorder)

                    totalY += it.height
                })
            }
        }
    }
}

class Radar(private val pane: ScrollPane) : Group() {
    private val terrain = pane.getChild(0) as Terrain

    private var lastKnownHeight = 0f

    init {
        this.width = 64f
        this.height = 256f

        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.RADAR_BACKGROUND)).also {
            it.setPosition(0f, 0f)
        })
    }

    override fun act(delta: Float) {
        super.act(delta)

        val ratioX = this.width / this.terrain.width
        val ratioY = this.height / this.terrain.height
        val x = this.pane.scrollX * ratioX
        val y = this.height - (this.pane.scrollY * ratioY)

        val maxHeight = min(this.pane.height, this.pane.scrollHeight) * ratioY

        // NOTE: maybe we could completely avoid allocating?

        if (this.lastKnownHeight != maxHeight) {
            this.children
                .filterIsInstance<Border>()
                .forEach { it.remove() }

            this.addBordersAroundViewport(x, y, maxHeight)

            this.lastKnownHeight = maxHeight
        } else {
            this.updateBordersAroundViewport(x, y, maxHeight)
        }
    }

    private fun addBordersAroundViewport(x: Float, y: Float, maxHeight: Float) {
        this.addActor(Border(BorderKind.TOP, true, maxHeight).also {
            it.setPosition(x, y - it.height / 2f)
        })
        this.addActor(Border(BorderKind.BOTTOM, true, maxHeight).also {
            it.setPosition(x, y - maxHeight - it.height / 2f)
        })
        this.addActor(Border(BorderKind.LEFT, false, maxHeight).also {
            it.setPosition(-it.width / 2f, y + it.width / 2f - it.height)
        })
        this.addActor(Border(BorderKind.RIGHT, false, maxHeight).also {
            it.setPosition(this.width - it.width / 2f, y + it.width / 2f - it.height)
        })
    }

    private fun updateBordersAroundViewport(x: Float, y: Float, maxHeight: Float) {
        this.children.filterIsInstance<Border>()
            .forEach {
                when (it.border) {
                    BorderKind.TOP -> it.setPosition(x, y - it.height / 2f)
                    BorderKind.BOTTOM -> it.setPosition(x, y - maxHeight - it.height / 2f)
                    BorderKind.LEFT -> it.setPosition(-it.width / 2f, y + it.width / 2f - it.height)
                    BorderKind.RIGHT -> it.setPosition(this.width - it.width / 2f, y + it.width / 2f - it.height)
                }
            }
    }
}