package typings.clsx

import typings.std.Record
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

object mod {
  
  inline def apply(inputs: ClassValue*): String = ^.asInstanceOf[js.Dynamic].apply(inputs.asInstanceOf[Seq[js.Any]]*).asInstanceOf[String]
  
  @JSImport("clsx", JSImport.Namespace)
  @js.native
  val ^ : js.Any = js.native
  
  inline def clsx(inputs: ClassValue*): String = ^.asInstanceOf[js.Dynamic].applyDynamic("clsx")(inputs.asInstanceOf[Seq[js.Any]]*).asInstanceOf[String]
  
  type ClassArray = js.Array[ClassValue]
  
  type ClassDictionary = Record[String, Any]
  
  /** 
  NOTE: Rewritten from type alias:
  {{{
  type ClassValue = clsx.clsx.ClassArray | clsx.clsx.ClassDictionary | string | number | bigint | null | boolean | undefined
  }}}
  to avoid circular code involving: 
  - clsx.clsx.ClassArray
  - clsx.clsx.ClassValue
  */
  type ClassValue = js.UndefOr[Any | ClassDictionary | String | Double | js.BigInt | Null | Boolean]
}
