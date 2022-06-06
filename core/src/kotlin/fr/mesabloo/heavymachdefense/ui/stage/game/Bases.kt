package fr.mesabloo.heavymachdefense.ui.stage.game

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

class AllyBase(level: Int) :
    Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ALLY_BASE, "base-${level.toString().padStart(2, '0')}"))
{

}

class EnemyBase : Image(stageAssetsManager.unsafeRegion(StageAssetsManager.ENEMY_BASE, "base-01")) {

}