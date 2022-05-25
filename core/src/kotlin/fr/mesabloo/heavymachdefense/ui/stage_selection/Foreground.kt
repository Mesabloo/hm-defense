package fr.mesabloo.heavymachdefense.ui.stage_selection

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.LevelSelectionAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.levelSelectionAssetsManager

class Foreground : Image(levelSelectionAssetsManager.texture(LevelSelectionAssetsManager.FOREGROUND))