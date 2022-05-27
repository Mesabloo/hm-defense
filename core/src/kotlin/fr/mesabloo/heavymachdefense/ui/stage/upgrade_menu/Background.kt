package fr.mesabloo.heavymachdefense.ui.stage.upgrade_menu

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.StageAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.stageAssetsManager

class BackgroundLeft : Image(stageAssetsManager.get(StageAssetsManager.UI.UPGRADE_MENU_LEFT))

class BackgroundRight : Image(stageAssetsManager.get(StageAssetsManager.UI.UPGRADE_MENU_RIGHT))