package fr.mesabloo.heavymachdefense.ui.stage

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Expo
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Queue
import fr.mesabloo.heavymachdefense.data.Builds
import fr.mesabloo.heavymachdefense.data.MachineKind
import fr.mesabloo.heavymachdefense.data.TurretKind
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.POSITION
import kotlin.reflect.KMutableProperty0

abstract class BuildItem(internal var elapsed: Float, internal val timeToBuild: Float)
class BuildMachineItem(val kind: MachineKind, val level: Int, builds: Builds) : BuildItem(0f, builds.machines[kind to level]!!.time)
class BuildTurretItem(val kind: TurretKind, val level: Int, builds: Builds) : BuildItem(0f, builds.turrets[kind to level]!!.time)

private class BuildItemActor(val item: BuildItem) : Group() {
    private companion object {
        const val PADDING = 2f
    }

    internal val gauge: Actor

    init {
        this.width = 64f
        this.height = 64f

        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_QUEUE_ITEM_BACKGROUND)).also {
            it.width = this.width * 2f
            it.height = this.height * 2f
            it.setPosition(-32f, -32f)
        })

        this.addActor(Label("TODO", Label.LabelStyle().also {
            it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_20_BLUE]
            it.fontColor = Color.MAGENTA
        }).also {
            it.setPosition(32f - it.width / 2f, 32f - it.height / 2f)
        })

        this.addActor(Image(stageAssetsManager.get(StageAssetsManager.UI.BUILD_QUEUE_GAUGE)).also {
            this.gauge = it

            it.setPosition(0f, PADDING)
            it.width = 0f
        })
    }

    override fun act(delta: Float) {
        this.gauge.width = (this.width - PADDING) * (this.item.elapsed / this.item.timeToBuild)

        super.act(delta)
    }
}

class BuildQueue(private val upgradeMenuShown: KMutableProperty0<Boolean>) : Group() {
    private companion object  {
        const val SLOT_HEIGHT = 64f
        const val SLOT_PADDING = 8f
    }

    private val queue = Queue<BuildItemActor>(7)

    private val tweenManager = TweenManager()

    init {
        Tween.registerAccessor(Actor::class.java, ActorAccessor())
    }

    fun getNumberOf(kind: MachineKind) =
        this.queue.filter { it.item is BuildMachineItem }
            .count { (it.item as BuildMachineItem).kind == kind }

    fun getNumberOf(kind: TurretKind) =
        this.queue.filter { it.item is BuildTurretItem }
            .count { (it.item as BuildTurretItem).kind == kind }

    fun build(item: BuildItem) {
        val actor = BuildItemActor(item)
        val slotsOccupied = this.queue.size

        if (slotsOccupied > 7) {
            actor.setPosition(2f, -(slotsOccupied.toFloat() - 7f) * (SLOT_HEIGHT + SLOT_PADDING))
        } else {
            actor.setPosition(2f, -SLOT_HEIGHT - SLOT_PADDING)
        }

        this.queue.addLast(actor)

        this.addActor(actor)

        Tween.to(actor, POSITION, 0.5f)
            .ease(Expo.OUT)
            .target(2f, this.height - (slotsOccupied.toFloat() + 1) * (SLOT_HEIGHT + SLOT_PADDING))
            .start(this.tweenManager)
    }

    override fun act(delta: Float) {
        if (!this.upgradeMenuShown.get()) {
            this.tweenManager.update(delta)

            var index = 0
            while (index < this.queue.size) {
                val item = this.queue[index]

                if (item.item.elapsed >= item.item.timeToBuild) {
                    if (this.queue.size > index) {
                        // in case there are more entries in the queue than the one we are removing
                        // move every other entry up by one item
                        for (index2 in (index + 1) until this.queue.size) {
                            val item2 = this.queue[index2]

                            if (this.tweenManager.containsTarget(item2, POSITION)) {
                                // if the item is already playing an animation, kill it
                                this.tweenManager.killTarget(item2, POSITION)
                            }

                            Tween.to(item2, POSITION, 0.5f)
                                .ease(Expo.OUT)
                                .target(2f, this.height - index2 * (SLOT_HEIGHT + SLOT_PADDING))
                                .start(this.tweenManager)
                        }
                    }

                    this.queue.removeIndex(index)
                    this.tweenManager.killTarget(item, POSITION)
                    item.remove()
                } else {
                    index += 1
                }

                item.item.elapsed += delta
            }

            super.act(delta)
        }
    }
}