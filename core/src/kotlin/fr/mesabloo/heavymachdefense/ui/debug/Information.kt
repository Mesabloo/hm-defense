package fr.mesabloo.heavymachdefense.ui.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Align
import fr.mesabloo.heavymachdefense.managers.assets.DebugAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.debugAssetsManager
import java.lang.management.ManagementFactory
import javax.management.Attribute
import javax.management.AttributeList
import javax.management.ObjectName
import kotlin.math.max


class DebugInfo : Table() {
    private val fps = Label("0 FPS", Label.LabelStyle().also {
        it.font = debugAssetsManager.font
        it.fontColor = Color.BLUE
    })
    private val memory = Label("0 MB used", Label.LabelStyle().also {
        it.font = debugAssetsManager.font
        it.fontColor = Color.BLUE
    })
    private val cpuLoad = Label("0%", Label.LabelStyle().also {
        it.font = debugAssetsManager.font
        it.fontColor = Color.BLUE
    })

    private var timeSinceLastUpdate = UPDATE_DELAY

    private val bg =
        Image(NinePatchDrawable(NinePatch(debugAssetsManager.get(DebugAssetsManager.BLACK_BG), 7, 7, 7, 7)))

    private companion object {
        const val OUTER_PADDING = 5f
        const val UPDATE_DELAY = 1f // every second
    }

    init {
        this.addActor(this.bg)
        this.add(this.fps).width(50f).center().left()
        this.add(this.cpuLoad).width(50f).center().left()
        this.add(this.memory).minWidth(70f).center().left()

        this.bg.setOrigin(Align.bottomLeft)
        this.bottom().left().pad(OUTER_PADDING)
    }

    override fun act(delta: Float) {
        val appFPS = Gdx.graphics.framesPerSecond
        val appMemory = Gdx.app.javaHeap / (1024 * 1024)
        val cpuUsage = getProcessCpuLoad()

        if (this.timeSinceLastUpdate > UPDATE_DELAY) {
            this.fps.setText("$appFPS FPS")
            this.fps.style.fontColor = if (appFPS >= 50) Color.GREEN else if (appFPS >= 30) Color.ORANGE else Color.RED

            this.memory.setText("$appMemory MB used")
            this.memory.style.fontColor =
                if (appMemory <= 50) Color.GREEN else if (appMemory <= 100) Color.ORANGE else Color.RED

            this.cpuLoad.setText("$cpuUsage%")
            this.cpuLoad.style.fontColor =
                if (cpuUsage <= 33) Color.GREEN else if (cpuUsage <= 66) Color.ORANGE else Color.RED

            super.act(delta)

            this.bg.setPosition(0f, 0f)
            this.bg.width = 50f + 50f + max(this.memory.width, 70f) + 2 * OUTER_PADDING
            this.bg.height =
                listOf(this.fps.height, this.memory.height, this.cpuLoad.height).maxOrNull()!! + 2 * OUTER_PADDING

            this.timeSinceLastUpdate -= UPDATE_DELAY
        }

        this.timeSinceLastUpdate += delta
    }

    override fun drawDebug(shapes: ShapeRenderer) {
        // prevent drawing debug
    }

    @Throws(Exception::class)
    fun getProcessCpuLoad(): Double {
        val mbs = ManagementFactory.getPlatformMBeanServer()
        val name = ObjectName.getInstance("java.lang:type=OperatingSystem")
        val list: AttributeList = mbs.getAttributes(name, arrayOf("ProcessCpuLoad"))
        if (list.isEmpty()) return Double.NaN
        val att: Attribute = list[0] as Attribute
        val value = att.value as Double

        // usually takes a couple of seconds before we get real values
        return if (value == -1.0) Double.NaN else (value * 1000).toInt() / 10.0
        // returns a percentage value with 1 decimal point precision
    }

}