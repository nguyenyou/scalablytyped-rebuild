package org.scalablytyped.converter.internal

import java.io.File
import java.nio.file.{Files, Path}

import cats.data.ValidatedNel
import io.circe._
import io.circe.syntax._

import scala.io.Source
import scala.util.control.NonFatal

object Json {

  /** Using circe's built-in parser instead of Jackson for Scala 3 compatibility.
   *  Note: This loses some lenient parsing features (comments, trailing commas, etc.)
   *  but provides better Scala 3 support.
   */
  object CustomCirceParser extends Parser {

    // Simple preprocessing to handle some common non-standard JSON features
    private def preprocessJson(input: String): String = {
      // First pass: remove comments and trailing commas
      val withoutComments = input
        .replaceAll("//.*", "") // Remove single-line comments
        .replaceAll("/\\*[\\s\\S]*?\\*/", "") // Remove multi-line comments
        .replaceAll(",\\s*}", "}") // Remove trailing commas in objects
        .replaceAll(",\\s*]", "]") // Remove trailing commas in arrays

      // Second pass: properly escape control characters in JSON strings
      val result = new StringBuilder()
      var inString = false
      var escaped = false
      var i = 0

      while (i < withoutComments.length) {
        val char = withoutComments.charAt(i)

        if (escaped) {
          result.append(char)
          escaped = false
        } else if (char == '\\') {
          result.append(char)
          escaped = true
        } else if (char == '"') {
          result.append(char)
          inString = !inString
        } else if (inString) {
          // We're inside a string, escape control characters
          char match {
            case '\n' => result.append("\\n")
            case '\r' => result.append("\\r")
            case '\t' => result.append("\\t")
            case '\b' => result.append("\\b")
            case '\f' => result.append("\\f")
            case c if c.isControl => result.append(f"\\u${c.toInt}%04x")
            case c => result.append(c)
          }
        } else {
          result.append(char)
        }

        i += 1
      }

      result.toString
    }

    final def parse(input: String): Either[ParsingFailure, Json] = {
      // First try parsing without preprocessing
      io.circe.parser.parse(input) match {
        case success @ Right(_) => success
        case Left(_) =>
          // If that fails, try with preprocessing
          io.circe.parser.parse(preprocessJson(input))
      }
    }

    final def parseFile(file: File): Either[ParsingFailure, Json] = {
      try {
        val content = scala.io.Source.fromFile(file, "UTF-8").mkString
        parse(content)
      } catch {
        case NonFatal(error) => Left(ParsingFailure(error.getMessage, error))
      }
    }

    final def parseByteArray(bytes: Array[Byte]): Either[ParsingFailure, Json] = {
      try {
        val content = new String(bytes, "UTF-8")
        parse(content)
      } catch {
        case NonFatal(error) => Left(ParsingFailure(error.getMessage, error))
      }
    }

    final def decodeByteArray[A: Decoder](bytes: Array[Byte]): Either[Error, A] =
      finishDecode[A](parseByteArray(bytes))

    final def decodeByteArrayAccumulating[A: Decoder](bytes: Array[Byte]): ValidatedNel[Error, A] =
      finishDecodeAccumulating[A](parseByteArray(bytes))

    final def decodeFile[A: Decoder](file: File): Either[Error, A] =
      finishDecode[A](parseFile(file))

    final def decodeFileAccumulating[A: Decoder](file: File): ValidatedNel[Error, A] =
      finishDecodeAccumulating[A](parseFile(file))
  }

  private val BOM = "\uFEFF"

  def force[T: Decoder](path: os.Path): T =
    force(path.toNIO)

  def force[T: Decoder](path: Path): T =
    apply(new String(Files.readAllBytes(path), constants.Utf8)) match {
      case Left(error) => sys.error(s"Error while parsing: $path: $error")
      case Right(t)    => t
    }

  def forceResource[T: Decoder](resource: String): T =
    Option(getClass.getResourceAsStream(resource)) match {
      case Some(is) =>
        val s = Source.fromInputStream(is, constants.Utf8.name)
        try force[T](s.mkString)
        finally s.close()
      case None => sys.error(s"Couldn't find resource $resource")
    }

  def force[T: Decoder](original: String): T =
    apply[T](original) match {
      case Left(error) => sys.error(s"Error while parsing: $error")
      case Right(t)    => t
    }

  def apply[T: Decoder](original: String): Either[Error, T] = {
    val str = original match {
      case withBom if withBom.startsWith(BOM) => withBom.replace(BOM, "")
      case ok                                 => ok
    }

    CustomCirceParser.decode[T](str)
  }

  def opt[T: Decoder](path: os.Path): Option[T] =
    if (files.exists(path)) Some(force[T](path)) else None

  def opt[T: Decoder](path: Path): Option[T] =
    if (Files.exists(path)) Some(force(path)) else None

//  def persist[V: Encoder](file: os.Path)(value: V): Synced =
//    persist(file.toNIO)(value)

//  def persist[V: Encoder](file: Path)(value: V): Synced =
//    files.softWrite(file)(_.append(value.asJson.noSpaces))
}