package fr.mesabloo.heavymachdefense.ui.stage.upgrade_menu

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.mesabloo.heavymachdefense.data.UpgradeKind
import fr.mesabloo.heavymachdefense.managers.FontManager
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.fontManager
import ktx.graphics.color

class CloseButton : ImageButton(ImageButtonStyle().also {
    it.up = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(UpgradeKind.CLOSE, false, false))
    it.down = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(UpgradeKind.CLOSE, true, false))
    it.disabled = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(UpgradeKind.CLOSE, false, true))
})

class UpgradeButton : ImageButton(ImageButtonStyle().also {
    it.up = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(UpgradeKind.UPGRADE, false, false))
    it.down = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(UpgradeKind.UPGRADE, true, false))
    it.disabled = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(UpgradeKind.UPGRADE, false, true))
})

class AbstractUpgradeLevelButton(
    val kind: UpgradeKind,
    var price: Long,
    var level: Pair<Long, Long>, // current / max
    var addition: Triple<String, Long, Long>? = null
) : ImageButton(ImageButtonStyle().also {
    it.up = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(kind, false, false))
    it.down = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(kind, true, false))
    it.checked = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(kind, true, false))
    it.disabled = TextureRegionDrawable(StageAssetsManager.UI.upgradeButton(kind, false, true))
}) {
    private val priceLabel = Label("", Label.LabelStyle().also {
        it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_16_WHITE]
        it.fontColor = color(1f, 1f, 1f)
    })
    private val levelLabel = Label("Lv. ${level.first}/${level.second}", Label.LabelStyle().also {
        it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_11_WHITE]
        it.fontColor = color(1f, 1f, 1f)
    })
    private val infoLabel = Label("", Label.LabelStyle().also {
        it.font = fontManager.bitmapFonts[FontManager.TREBUCHET_MS_BOLD_11_WHITE]
        it.fontColor = color(1f, 1f, 1f)
    })

    init {
        this.addActor(this.priceLabel)
        this.addActor(this.levelLabel)
        this.addActor(this.infoLabel)
    }

    override fun act(delta: Float) {
        super.act(delta)

        this.priceLabel.setText("$price${if (price <= 999999) " cr" else ""}")
        this.levelLabel.setText("Lv. ${level.first}/${level.second}")
        this.infoLabel.setText(this.addition?.let { "${it.first} : ${it.second}(+${it.third})" } ?: "")

        this.priceLabel.setPosition(227f - this.priceLabel.width, this.height / 2f - this.priceLabel.height / 2f)
        this.levelLabel.setPosition(320f, this.height / 2f - this.levelLabel.height / 2f)
        this.infoLabel.setPosition(378f, this.height / 2f - this.infoLabel.height / 2f)
    }
}