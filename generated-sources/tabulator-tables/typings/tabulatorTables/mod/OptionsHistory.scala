package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsHistory extends StObject {
  
  /** Enable user interaction history functionality */
  var history: js.UndefOr[Boolean] = js.undefined
}
object OptionsHistory {
  
  inline def apply(): OptionsHistory = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsHistory]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsHistory] (val x: Self) extends AnyVal {
    
    inline def setHistory(value: Boolean): Self = StObject.set(x, "history", value.asInstanceOf[js.Any])
    
    inline def setHistoryUndefined: Self = StObject.set(x, "history", js.undefined)
  }
}
