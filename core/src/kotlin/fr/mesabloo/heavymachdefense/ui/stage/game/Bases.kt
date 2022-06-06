package fr.mesabloo.heavymachdefense.ui.stage.game

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Linear
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.data.models.BaseModel
import fr.mesabloo.heavymachdefense.data.models.RadarModel
import fr.mesabloo.heavymachdefense.data.models.WeaponModel
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.ROTATION
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AllyBase(defLevel: Int, atkLevel: Int) : Group() {
    private val tweenManager = TweenManager()

    private lateinit var base: Actor
    private lateinit var radar: Actor
    private lateinit var weapon1: Actor
    private lateinit var weapon2: Actor

    var defLevel = defLevel
        set(value) {
            updateDefenseSkin(value)
            field = value
        }
    var atkLevel = atkLevel
        set(value) {
            updateAttackSkins(value)
            field = value
        }

    init {
        this.updateDefenseSkin(defLevel)
        this.updateAttackSkins(atkLevel)
    }

    override fun act(delta: Float) {
        this.tweenManager.update(delta)

        super.act(delta)
    }

    private fun updateDefenseSkin(newLevel: Int) {
        val oAtkLevel = atkLevel.coerceIn(1..7).toString().padStart(2, '0')
        val oDefLevel = newLevel.coerceIn(1..7).toString().padStart(2, '0')

        val texture = stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "base-$oDefLevel")

        val radarModel = Json.decodeFromString<RadarModel>(
            Gdx.files.internal("data/models/bases/$oAtkLevel/radar.json").readString()
        )
        val weaponModel = Json.decodeFromString<WeaponModel>(
            Gdx.files.internal("data/models/bases/$oAtkLevel/weapon.json").readString()
        )
        val baseModel = Json.decodeFromString<BaseModel>(
            Gdx.files.internal("data/models/bases/$oDefLevel/base.json").readString()
        )

        if (this::base.isInitialized) {
            this.base.remove()
        }
        this.addActor(Image(texture).also {
            this.base = it

            this.width = it.width
            this.height = it.height
        })

        if (this::radar.isInitialized) {
            this.radar.setOrigin(radarModel.originX, radarModel.originY)
            this.radar.setPosition(baseModel.radar.x - radarModel.originX, baseModel.radar.y - radarModel.originY)

            this.radar.zIndex = 50000
        }

        if (this::weapon1.isInitialized) {
            this.weapon1.setOrigin(weaponModel.originX, weaponModel.originY)
            this.weapon1.setPosition(
                baseModel.weapons[0].x - weaponModel.originX,
                baseModel.weapons[0].y - weaponModel.originY
            )
            this.weapon1.rotation = 90f

            this.weapon1.zIndex = 50000
        }

        if (this::weapon2.isInitialized) {
            this.weapon2.setOrigin(weaponModel.originX, weaponModel.originY)
            this.weapon2.setPosition(
                baseModel.weapons[1].x - weaponModel.originX,
                baseModel.weapons[1].y - weaponModel.originY
            )
            this.weapon2.rotation = 90f

            this.weapon2.zIndex = 50000
        }
    }

    private fun updateAttackSkins(newLevel: Int) {
        val oAtkLevel = newLevel.coerceIn(1..7).toString().padStart(2, '0')
        val oDefLevel = defLevel.coerceIn(1..7).toString().padStart(2, '0')

        val radarModel = Json.decodeFromString<RadarModel>(
            Gdx.files.internal("data/models/bases/$oAtkLevel/radar.json").readString()
        )
        val weaponModel = Json.decodeFromString<WeaponModel>(
            Gdx.files.internal("data/models/bases/$oAtkLevel/weapon.json").readString()
        )
        val baseModel = Json.decodeFromString<BaseModel>(
            Gdx.files.internal("data/models/bases/$oDefLevel/base.json").readString()
        )

        val radarTexture = stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "radar-$oAtkLevel")
        val weaponTexture1 = stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "weapon-$oAtkLevel")
        val weaponTexture2 = stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "weapon-$oAtkLevel")

        if (this::radar.isInitialized) {
            this.radar.remove()
        }
        this.addActor(Image(radarTexture).also {
            this.radar = it
        })

        if (this::weapon1.isInitialized) {
            this.weapon1.remove()
        }
        this.addActor(Image(weaponTexture1).also {
            this.weapon1 = it
        })

        if (this::weapon2.isInitialized) {
            this.weapon2.remove()
        }
        this.addActor(Image(weaponTexture2).also {
            this.weapon2 = it
        })

        if (!this.tweenManager.containsTarget(this.radar)) {
            Tween.registerAccessor(Actor::class.java, ActorAccessor())
            Tween.to(this.radar, ROTATION, 0.60f)
                .target(360f)
                .ease(Linear.INOUT)
                .repeat(Tween.INFINITY, 0f)
                .start(this.tweenManager)
        }

        (this.radar as Image).drawable = TextureRegionDrawable(radarTexture)
        this.radar.setOrigin(radarModel.originX, radarModel.originY)
        this.radar.setPosition(baseModel.radar.x - radarModel.originX, baseModel.radar.y - radarModel.originY)

        (this.weapon1 as Image).drawable = TextureRegionDrawable(weaponTexture1)
        this.weapon1.setOrigin(weaponModel.originX, weaponModel.originY)
        this.weapon1.setPosition(
            baseModel.weapons[0].x - weaponModel.originX,
            baseModel.weapons[0].y - weaponModel.originY
        )
        this.weapon1.rotation = 90f

        (this.weapon2 as Image).drawable = TextureRegionDrawable(weaponTexture2)
        this.weapon2.setOrigin(weaponModel.originX, weaponModel.originY)
        this.weapon2.setPosition(
            baseModel.weapons[1].x - weaponModel.originX,
            baseModel.weapons[1].y - weaponModel.originY
        )
        this.weapon2.rotation = 90f
    }
}

class EnemyBase : Group() {
    private val radar: Actor

    private val tweenManager = TweenManager()

    init {
        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ENEMY_BASE, "base-01")).also {
            it.setPosition(0f, 0f)

            this.width = it.width
            this.height = it.height
        })

        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ENEMY_BASE, "radar-01")).also {
            this.radar = it

            it.setOrigin(21f, 10f)
            it.setPosition(188f - it.originX, 83f - it.originY)
        })

        Tween.registerAccessor(Actor::class.java, ActorAccessor())
        Tween.to(this.radar, ROTATION, 0.60f)
            .target(360f)
            .ease(Linear.INOUT)
            .repeat(Tween.INFINITY, 0f)
            .start(this.tweenManager)
    }

    override fun act(delta: Float) {
        this.tweenManager.update(delta)

        super.act(delta)
    }
}