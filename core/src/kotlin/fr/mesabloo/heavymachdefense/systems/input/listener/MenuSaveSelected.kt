package fr.mesabloo.heavymachdefense.systems.input.listener

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import fr.mesabloo.heavymachdefense.components.TextureComponent
import fr.mesabloo.heavymachdefense.components.input.MouseInputComponent
import fr.mesabloo.heavymachdefense.components.saves.SaveComponent
import fr.mesabloo.heavymachdefense.managers.assets.menuAssetsManager
import fr.mesabloo.heavymachdefense.screens.MenuScreen
import ktx.ashley.allOf
import ktx.ashley.get

class MenuSaveSelected(private val screen: MenuScreen, private val engine: Engine, private val entity: Entity, private val index: Int) :
        () -> Unit {
    @Suppress("NAME_SHADOWING")
    override fun invoke() {
        val textureComponent = entity[TextureComponent.mapper]!!
        val saveComponent = entity[SaveComponent.mapper]!!

        if (saveComponent.focused) {
            TODO("open save and go to level selection screen")
        } else {
            val allSaveEntities =
                this.engine.getEntitiesFor(allOf(SaveComponent::class, MouseInputComponent::class).get())
                    .filter { it[SaveComponent.mapper]!!.save != saveComponent.save }

            saveComponent.focused = true
            textureComponent.texture = menuAssetsManager.getSlot(true)
            screen.focusedIndex = index

            for (entity in allSaveEntities) {
                val saveComponent = entity[SaveComponent.mapper]!!
                saveComponent.focused = false

                val textureComponent = entity[TextureComponent.mapper]!!
                textureComponent.texture = menuAssetsManager.getSlot(false)
            }
        }
    }
}
