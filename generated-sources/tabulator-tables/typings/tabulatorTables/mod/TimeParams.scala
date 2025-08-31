package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait TimeParams
  extends StObject
     with SharedEditorParams
     with _EditorParams {
  
  var format: js.UndefOr[String] = js.undefined
  
  var verticalNavigation: js.UndefOr[VerticalNavigationOptions] = js.undefined
}
object TimeParams {
  
  inline def apply(): TimeParams = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[TimeParams]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: TimeParams] (val x: Self) extends AnyVal {
    
    inline def setFormat(value: String): Self = StObject.set(x, "format", value.asInstanceOf[js.Any])
    
    inline def setFormatUndefined: Self = StObject.set(x, "format", js.undefined)
    
    inline def setVerticalNavigation(value: VerticalNavigationOptions): Self = StObject.set(x, "verticalNavigation", value.asInstanceOf[js.Any])
    
    inline def setVerticalNavigationUndefined: Self = StObject.set(x, "verticalNavigation", js.undefined)
  }
}
