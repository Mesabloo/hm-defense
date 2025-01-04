package io.github.mesabloo.hmdefense.assets.loaders

import com.badlogic.gdx.assets.loaders.{AsynchronousAssetLoader, FileHandleResolver}
import com.badlogic.gdx.assets.{AssetDescriptor, AssetLoaderParameters, AssetManager}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils
import io.github.mesabloo.hmdefense.data.MapGeometry
import spack.Spack

import scala.compiletime.uninitialized

final class MapGeometryLoaderParameters
    extends AssetLoaderParameters[MapGeometry]

final class MapGeometryLoader(resolver: FileHandleResolver)
    extends AsynchronousAssetLoader[MapGeometry, MapGeometryLoaderParameters](
      resolver
    ):
  private var geometry: MapGeometry = uninitialized

  override def loadAsync(
      assetManager: AssetManager,
      s: String,
      fileHandle: FileHandle,
      p: MapGeometryLoaderParameters
  ): Unit =
    this.geometry = null
    this.geometry =
      Spack.unpackTo[MapGeometry](fileHandle.readBytes()).toEither match
        case Left(err)    => throw err
        case Right(value) => value

  override def loadSync(
      assetManager: AssetManager,
      s: String,
      fileHandle: FileHandle,
      p: MapGeometryLoaderParameters
  ): MapGeometry =
    val geometry = this.geometry
    this.geometry = null
    geometry

  override def getDependencies(
      s: String,
      fileHandle: FileHandle,
      p: MapGeometryLoaderParameters
  ): utils.Array[AssetDescriptor[_]] = null
