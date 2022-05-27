package fr.mesabloo.heavymachdefense.managers.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import fr.mesabloo.heavymachdefense.data.getBackgroundForLevel
import ktx.assets.load

class StageAssetsManager : Disposable {
    private var stageLevel: Int? = null

    companion object {
        private fun backgrounds(level: Int): Pair<String, String> =
            getBackgroundForLevel(level).let {
                val nb = it.toString().padStart(2, '0')
                Pair("gfx/terrains/$nb/01.jpg", "gfx/terrains/$nb/02.jpg")
            }

        const val MACHINE_BODIES = "gfx/models/machines/bodies.atlas"
        const val MACHINE_WEAPONS = "gfx/models/machines/weapons.atlas"
        // TODO: feet

        const val ALLY_TURRET_BODIES = "gfx/models/turrets/ally-bodies.atlas"
        const val ALLY_TURRET_WEAPONS = "gfx/models/turrets/ally-weapons.atlas"
        const val ENEMY_TURRET_BODIES = "gfx/models/turrets/enemy-bodies.atlas"
        const val ENEMY_TURRET_WEAPONS = "gfx/models/turrets/enemy-weapons.atlas"

        const val TANK_BODIES = "gfx/models/tanks/enemy-bodies.atlas"
        const val TANK_WEAPONS = "gfx/models/tanks/enemy-weapons.atlas"

        const val ALLY_PLANE = "gfx/models/planes/plane.png"
        const val ENEMY_PLANE = "gfx/models/planes/enemy-planes.atlas"

        const val SHIPS = "gfx/models/other/ships.atlas"

        const val ALLY_BASE = "gfx/models/base/ally-base.atlas"
        const val ENEMY_BASE = "gfx/models/base/enemy-base.atlas"
    }

    //////////////////////////////////////////////

    object UI {
        const val BUILD_SLOT = "gfx/ui/game/build-slot.png"
        const val MACH_SLOT = "gfx/ui/game/mach-slot.png"
        const val CONTROLS = "gfx/ui/game/controls.png"
        const val HP_GAUGE = "gfx/ui/game/hp-gauge.png"
        const val BUTTONS = "gfx/ui/buttons/stage.atlas"
        const val TITLE = "gfx/ui/game/title.atlas"
        const val MENU_BUTTONS = "gfx/ui/game/menu-buttons.atlas"
        const val RADAR_MARKS = "gfx/ui/game/radar/marks.atlas"
        const val RADAR_BACKGROUND = "gfx/ui/game/radar/background.png"
        const val RADAR_BORDER = "gfx/ui/game/radar/border.png"
        const val PLAYER_HP_GAUGE = "gfx/ui/game/player-hp-gauge.png"
        const val ENEMY_HP_GAUGE = "gfx/ui/game/enemy-hp-gauge.png"

        enum class ButtonKind(internal val str: String) {
            MENU("menu"),
            BASE_UPGRADE("base-upgrade")
        }

        enum class TitleKind(internal val str: String) {
            BUILD_MACH("build-mach"),
            SPECIAL_ATTACK("special-attack")
        }

        fun button(kind: ButtonKind, isSelected: Boolean): TextureRegion =
            assetManager.get<TextureAtlas>(BUTTONS)
                .findRegion("${kind.str}${if (isSelected) "-selected" else ""}")

        fun title(kind: TitleKind): TextureRegion =
            assetManager.get<TextureAtlas>(TITLE)
                .findRegion(kind.str)

        fun unsafeAnimationTile(path: String, tile: String): TextureRegion =
            assetManager.get<TextureAtlas>(path).findRegion(tile)
    }

    private fun allAtlases() = listOf(
        MACHINE_BODIES,
        MACHINE_WEAPONS,
        ALLY_TURRET_BODIES,
        ALLY_TURRET_WEAPONS,
        ENEMY_TURRET_BODIES,
        ENEMY_TURRET_WEAPONS,
        TANK_BODIES,
        TANK_WEAPONS,
        ENEMY_PLANE,
        SHIPS,
        ALLY_BASE,
        ENEMY_BASE,
        UI.BUTTONS,
        UI.TITLE,
        UI.MENU_BUTTONS,
        UI.RADAR_MARKS
    )

    private fun allTextures() = this.stageLevel?.let {
        val (bg1, bg2) = backgrounds(it)

        listOf(
            bg1,
            bg2,
            ALLY_PLANE,
            UI.BUILD_SLOT,
            UI.MACH_SLOT,
            UI.CONTROLS,
            UI.HP_GAUGE,
            UI.RADAR_BORDER,
            UI.RADAR_BACKGROUND,
            UI.PLAYER_HP_GAUGE,
            UI.ENEMY_HP_GAUGE
        )
    } ?: listOf()

    fun preload(level: Int) {
        this.stageLevel = level

        this.allTextures().forEach { assetManager.load<Texture>(it) }
        this.allAtlases().forEach { assetManager.load<TextureAtlas>(it) }
    }

    fun isFullyLoaded(): Boolean =
        this.stageLevel != null
                && this.allTextures().all { assetManager.isLoaded(it) }
                && this.allAtlases().all { assetManager.isLoaded(it) }

    fun get(path: String): TextureRegion = TextureRegion(assetManager.get<Texture>(path))

    override fun dispose() {
        if (this.stageLevel != null) {
            val (bg1, bg2) = backgrounds(this.stageLevel!!)

            assetManager.unload(bg1)
            assetManager.unload(bg2)
        }
    }

    fun background(): Pair<TextureRegion, TextureRegion>? = this.stageLevel?.let { level ->
        backgrounds(level).let { Pair(this.get(it.first), this.get(it.second)) }
    }
}

val stageAssetsManager by lazy { StageAssetsManager() }