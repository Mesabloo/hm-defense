package io.github.mesabloo.hmdefense.assets.loaders

import com.badlogic.gdx.assets.loaders.{AsynchronousAssetLoader, FileHandleResolver}
import com.badlogic.gdx.assets.{AssetDescriptor, AssetLoaderParameters, AssetManager}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils
import io.github.mesabloo.hmdefense.data.models.MachineModel
import spack.Spack

import scala.compiletime.uninitialized

private class MachineModelParameter extends AssetLoaderParameters[MachineModel]

/** Asynchronous [[AssetManager]] loader for [[MachineModel]]s.
  *
  * @param resolver
  *   How to resolve the file paths given to the loader.
  */
final class MachineModelLoader(resolver: FileHandleResolver)
    extends AsynchronousAssetLoader[MachineModel, MachineModelParameter](
      resolver
    ):
  private var model: MachineModel = uninitialized

  override def loadAsync(
      assetManager: AssetManager,
      s: String,
      fileHandle: FileHandle,
      p: MachineModelParameter
  ): Unit =
    this.model = null
    this.model =
      Spack.unpackTo[MachineModel](fileHandle.readBytes()).toEither match
        case Left(value)  => throw value
        case Right(value) => value

  override def loadSync(
      assetManager: AssetManager,
      s: String,
      fileHandle: FileHandle,
      p: MachineModelParameter
  ): MachineModel =
    val model = this.model
    this.model = null
    model

  override def getDependencies(
      s: String,
      fileHandle: FileHandle,
      p: MachineModelParameter
  ): utils.Array[AssetDescriptor[_]] = null
