package org.scalablytyped.converter
package internal
package importer

import io.circe013.{Decoder, Encoder}
import org.scalablytyped.converter.internal.scalajs.Name
import org.scalablytyped.converter.internal.ts.TsIdentLibrary

import scala.collection.immutable.SortedSet

case class ConversionOptions(
    useScalaJsDomTypes:       Boolean,
    outputPackage:            Name,
    stdLibs:                  SortedSet[String],
    ignored:                  SortedSet[String],
    enableLongApplyMethod:    Boolean,
    privateWithin:            Option[Name],
    useDeprecatedModuleNames: Boolean,
) {
  val ignoredLibs: Set[TsIdentLibrary] =
    ignored.map(TsIdentLibrary.apply)

  val ignoredModulePrefixes: Set[List[String]] =
    ignored.map(_.split("/").toList)
}

object ConversionOptions {
  implicit val encodes: Encoder[ConversionOptions] = io.circe013.generic.semiauto.deriveEncoder
  implicit val decodes: Decoder[ConversionOptions] = io.circe013.generic.semiauto.deriveDecoder
}