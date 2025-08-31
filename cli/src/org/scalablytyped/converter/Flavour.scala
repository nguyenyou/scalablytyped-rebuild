package org.scalablytyped.converter

import io.circe.{Decoder, Encoder}

sealed abstract class Flavour(val id: String)

object Flavour {
  case object Normal extends Flavour("normal")

  val All: List[Flavour] =
    List(Normal)

  val byName: Map[String, Flavour] =
    All.map(f => f.id -> f).toMap

  implicit val encodes: Encoder[Flavour] = Encoder[String].contramap(_.id)
  implicit val decodes: Decoder[Flavour] =
    Decoder[String].emap(str => byName.get(str).toRight(s"flavour '$str' not among ${byName.keys}"))
}