package fr.mesabloo.heavymachdefense.listeners.stage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import ktx.actors.alpha
import kotlin.reflect.KMutableProperty0

class ShowBuildSideMenu(
    private val titleKind: KMutableProperty0<StageAssetsManager.UI.TitleKind>,
    val machineSlots: Group,
    val specialSlots: Group
) : ClickListener() {
    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) =
        !event.isCancelled

    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
        Gdx.app.debug(this.javaClass.simpleName, "Clicked")

        this.titleKind.set(StageAssetsManager.UI.TitleKind.BUILD_MACH)

        this.machineSlots.alpha = 1f
        this.machineSlots.touchable = Touchable.enabled

        this.specialSlots.alpha = 0f
        this.specialSlots.touchable = Touchable.disabled
    }
}

class ShowSpecialSideMenu(
    private val titleKind: KMutableProperty0<StageAssetsManager.UI.TitleKind>,
    val machineSlots: Group,
    val specialSlots: Group
) : ClickListener() {
    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) =
        !event.isCancelled

    override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
        Gdx.app.debug(this.javaClass.simpleName, "Clicked")

        this.titleKind.set(StageAssetsManager.UI.TitleKind.SPECIAL_ATTACK)

        this.machineSlots.alpha = 0f
        this.machineSlots.touchable = Touchable.disabled

        this.specialSlots.alpha = 1f
        this.specialSlots.touchable = Touchable.enabled
    }
}