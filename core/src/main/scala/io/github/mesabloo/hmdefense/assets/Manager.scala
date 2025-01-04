package io.github.mesabloo.hmdefense.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.BitmapFontLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.BitmapFont
import io.github.mesabloo.hmdefense.assets.loaders.{MachineModelLoader, MapGeometryLoader}
import io.github.mesabloo.hmdefense.config.{debugSwitch, whenDebug}
import io.github.mesabloo.hmdefense.data.MapGeometry
import io.github.mesabloo.hmdefense.data.models.MachineModel

private class DebugAssetManager extends AssetManager:
  override def load[T](fileName: String, `type`: Class[T]): Unit =
    whenDebug:
      if !super.contains(fileName) then
        Gdx.app.debug(
          getClass.getCanonicalName,
          s"Loading asset '$fileName' as ${`type`.getSimpleName}…"
        )
    super.load(fileName, `type`)

  override def unload(fileName: String): Unit =
    whenDebug:
      if super.contains(fileName) then
        Gdx.app.debug(
          getClass.getCanonicalName,
          s"Unloading asset '$fileName'…"
        )
    super.unload(fileName)

/** The asset manager used to load [[com.badlogic.gdx.graphics.Texture]]s or
  * other stuff.
  *
  * The variable is marked no-inline to prevent the Scala compiler from
  * modifying the semantics of assets loading.
  */
@noinline
val assetManager: AssetManager = {
  val manager = debugSwitch { DebugAssetManager() } { AssetManager() }
  val resolver = InternalFileHandleResolver()

  // Setup so that we can load bitmap fonts (.fnt + .png files) with our asset manager.
  manager.setLoader(classOf[BitmapFont], BitmapFontLoader(resolver))
  // Allow loading msgpack-ed models with our asset manager.
  manager.setLoader(classOf[MachineModel], MachineModelLoader(resolver))
  // Allow loading msgpack-ed map geometries with our asset manager.
  manager.setLoader(classOf[MapGeometry], MapGeometryLoader(resolver))

  manager
}
