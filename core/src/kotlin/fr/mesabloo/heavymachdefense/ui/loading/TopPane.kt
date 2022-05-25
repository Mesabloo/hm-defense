package fr.mesabloo.heavymachdefense.ui.loading

import com.badlogic.gdx.scenes.scene2d.ui.Image
import fr.mesabloo.heavymachdefense.managers.assets.LoadingAssetsManager
import fr.mesabloo.heavymachdefense.managers.assets.loadingAssetsManager

class TopPane : Image(loadingAssetsManager.texture(LoadingAssetsManager.LOADING_TOP))