package fr.mesabloo.heavymachdefense.ui.stage.game

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import aurelienribon.tweenengine.equations.Linear
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.data.models.BaseModel
import fr.mesabloo.heavymachdefense.data.models.RadarModel
import fr.mesabloo.heavymachdefense.data.models.WeaponModel
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor
import fr.mesabloo.heavymachdefense.tweens.ActorAccessor.Companion.ROTATION
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AllyBase(var defLevel: Int, var atkLevel: Int) : Group() {
    private val tweenManager = TweenManager()

    private val radar: Actor
    private val weapon1: Actor
    private val weapon2: Actor

    init {
        val oDefLevel = defLevel.coerceIn(1..7).toString().padStart(2, '0')
        val oAtkLevel = atkLevel.coerceIn(1..7).toString().padStart(2, '0')

        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "base-$oDefLevel")).also {
            this.width = it.width
            this.height = it.height
        })

        val radarModel = Json.decodeFromString<RadarModel>(
            Gdx.files.internal("data/models/bases/$oAtkLevel/radar.json").readString()
        )
        val weaponModel = Json.decodeFromString<WeaponModel>(
            Gdx.files.internal("data/models/bases/$oAtkLevel/weapon.json").readString()
        )
        val baseModel =
            Json.decodeFromString<BaseModel>(Gdx.files.internal("data/models/bases/$oDefLevel/base.json").readString())

        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "radar-$oAtkLevel")).also {
            this.radar = it

            it.setOrigin(radarModel.originX, radarModel.originY)
            it.setPosition(baseModel.radar.x - radarModel.originX, baseModel.radar.y - radarModel.originY)
        })
        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "weapon-$oAtkLevel")).also {
            this.weapon1 = it

            it.setOrigin(weaponModel.originX, weaponModel.originY)
            it.setPosition(baseModel.weapons[0].x - weaponModel.originX, baseModel.weapons[0].y - weaponModel.originY)
            it.rotation = 90f
        })
        this.addActor(Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "weapon-$oAtkLevel")).also {
            this.weapon2 = it

            it.setOrigin(weaponModel.originX, weaponModel.originY)
            it.setPosition(baseModel.weapons[1].x - weaponModel.originX, baseModel.weapons[1].y - weaponModel.originY)
            it.rotation = 90f
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