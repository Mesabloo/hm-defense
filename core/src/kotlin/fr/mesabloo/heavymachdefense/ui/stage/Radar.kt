package fr.mesabloo.heavymachdefense.ui.stage

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.ui.stage.game.AllyBase
import fr.mesabloo.heavymachdefense.ui.stage.game.EnemyBase
import kotlin.math.min


private enum class BorderKind {
    TOP, BOTTOM, LEFT, RIGHT
}

private class Border(val border: BorderKind, private val isHorizontal: Boolean, maxHeight: Float) : Group() {
    private val tile = TiledDrawable(stageAssetsManager.get(StageAssetsManager.UI.RADAR_BORDER))

    private companion object {
        const val BETWEEN_PADDING = 4f
    }

    init {
        if (this.isHorizontal) {
            this.width = 64f
            this.height = 4f
        } else {
            this.width = 4f
            this.height = maxHeight + this.width
        }

//        this.addActor(mkImage().also {
//            if (isHorizontal) {
//                it.setPosition(-it.width, -it.height + it.width)
//            } else {
//                it.setPosition(0f, 0f)
//            }
//        })
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (this.isHorizontal) {
            this.tile.setPadding(0f, BETWEEN_PADDING, 0f, BETWEEN_PADDING)
        } else {
            this.tile.setPadding(BETWEEN_PADDING, 0f, BETWEEN_PADDING, 0f)
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        this.tile.draw(batch, this.x, this.y, this.width, this.height)
    }
}

class BaseMarker(val isAlly: Boolean) :
    Image(stageAssetsManager.unsafeRegion(StageAssetsManager.UI.RADAR_MARKS, if (isAlly) "ally-base" else "enemy-base"))

class Radar(private val pane: ScrollPane) : Group() {
    private val terrain = pane.getChild(0) as Terrain

    private lateinit var allyBaseActor: Actor
    private lateinit var enemyBaseActor: Actor

    private lateinit var allyBaseMarker: BaseMarker
    private lateinit var enemyBaseMarker: BaseMarker

    init {
        this.width = 64f
        this.height = 256f

        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.RADAR_BACKGROUND)).also {
            it.setPosition(0f, 0f)
        })

        val ratioX = this.width / this.terrain.width
        val ratioY = this.height / this.terrain.height
        val x = this.pane.scrollX * ratioX
        val y = this.height - (this.pane.scrollY * ratioY)

        val maxHeight = min(this.pane.height, this.pane.scrollHeight) * ratioY

        this.addBordersAroundViewport(x, y, maxHeight)

        this.addOrUpdateBaseMarkers(ratioX, ratioY)
    }

    override fun act(delta: Float) {
        super.act(delta)

        val ratioX = this.width / this.terrain.width
        val ratioY = this.height / this.terrain.height
        val x = this.pane.scrollX * ratioX
        val y = this.height - (this.pane.scrollY * ratioY)

        val maxHeight = min(this.pane.height, this.pane.scrollHeight) * ratioY

        this.updateBordersAroundViewport(x, y, maxHeight)

        this.addOrUpdateBaseMarkers(ratioX, ratioY)
    }

    private fun addOrUpdateBaseMarkers(ratioX: Float, ratioY: Float) {
        if (!this::allyBaseActor.isInitialized || !this::enemyBaseActor.isInitialized) {
            this.terrain.children.forEach {
                when (it) {
                    is AllyBase -> this.allyBaseActor = it
                    is EnemyBase -> this.enemyBaseActor = it
                    else -> {
                    }
                }
            }
        }

        if (!this::allyBaseMarker.isInitialized && this::allyBaseActor.isInitialized) {
            this.addActor(BaseMarker(true).also {
                this.allyBaseMarker = it

                it.setPosition(
                    this.allyBaseActor.x * ratioX + this.allyBaseActor.width / 2f * ratioX - it.width / 2f,
                    this.allyBaseActor.y * ratioY + this.allyBaseActor.height / 2f * ratioY - it.height / 2f
                )
            })
        } else if (this::allyBaseMarker.isInitialized) {
            this.allyBaseMarker.setPosition(
                this.allyBaseActor.x * ratioX + this.allyBaseActor.width / 2f * ratioX - this.allyBaseMarker.width / 2f,
                this.allyBaseActor.y * ratioY + this.allyBaseActor.height / 2f * ratioY - this.allyBaseMarker.height / 2f
            )
        }

        if (!this::enemyBaseMarker.isInitialized && this::enemyBaseActor.isInitialized) {
            this.addActor(BaseMarker(false).also {
                this.enemyBaseMarker = it

                it.setPosition(
                    this.enemyBaseActor.x * ratioX + this.enemyBaseActor.width / 2f * ratioX - it.width / 2f,
                    this.enemyBaseActor.y * ratioY + this.enemyBaseActor.height / 2f * ratioY - it.height / 2f
                )
            })
        } else if (this::enemyBaseMarker.isInitialized) {
            this.enemyBaseMarker.setPosition(
                this.enemyBaseActor.x * ratioX + this.enemyBaseActor.width / 2f * ratioX - this.enemyBaseMarker.width / 2f,
                this.enemyBaseActor.y * ratioY + this.enemyBaseActor.height / 2f * ratioY - this.enemyBaseMarker.height / 2f
            )
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
            it.setPosition(-it.width / 2f, y - it.height)
        })
        this.addActor(Border(BorderKind.RIGHT, false, maxHeight).also {
            it.setPosition(this.width - it.width / 2f, y - it.height)
        })
    }

    private fun updateBordersAroundViewport(x: Float, y: Float, maxHeight: Float) {
        this.children.filterIsInstance<Border>()
            .forEach {
                when (it.border) {
                    BorderKind.TOP -> {
                        it.setPosition(x, y - it.height / 2f)
                        it.setSize(it.width, it.height)
                    }
                    BorderKind.BOTTOM -> {
                        it.setPosition(x, y - maxHeight - it.height / 2f)
                        it.setSize(it.width, it.height)
                    }
                    BorderKind.LEFT -> {
                        it.setPosition(-it.width / 2f, y - it.height)
                        it.setSize(it.width, maxHeight)
                    }
                    BorderKind.RIGHT -> {
                        it.setPosition(this.width - it.width / 2f, y - it.height)
                        it.setSize(it.width, maxHeight)
                    }
                }
            }
    }
}