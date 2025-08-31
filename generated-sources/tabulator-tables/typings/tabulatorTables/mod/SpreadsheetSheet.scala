package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait SpreadsheetSheet extends StObject {
  
  var columns: js.UndefOr[Double] = js.undefined
  
  var data: js.Array[js.Array[Any]]
  
  var key: String
  
  var rows: js.UndefOr[Double] = js.undefined
  
  var title: String
}
object SpreadsheetSheet {
  
  inline def apply(data: js.Array[js.Array[Any]], key: String, title: String): SpreadsheetSheet = {
    val __obj = js.Dynamic.literal(data = data.asInstanceOf[js.Any], key = key.asInstanceOf[js.Any], title = title.asInstanceOf[js.Any])
    __obj.asInstanceOf[SpreadsheetSheet]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: SpreadsheetSheet] (val x: Self) extends AnyVal {
    
    inline def setColumns(value: Double): Self = StObject.set(x, "columns", value.asInstanceOf[js.Any])
    
    inline def setColumnsUndefined: Self = StObject.set(x, "columns", js.undefined)
    
    inline def setData(value: js.Array[js.Array[Any]]): Self = StObject.set(x, "data", value.asInstanceOf[js.Any])
    
    inline def setDataVarargs(value: js.Array[Any]*): Self = StObject.set(x, "data", js.Array(value*))
    
    inline def setKey(value: String): Self = StObject.set(x, "key", value.asInstanceOf[js.Any])
    
    inline def setRows(value: Double): Self = StObject.set(x, "rows", value.asInstanceOf[js.Any])
    
    inline def setRowsUndefined: Self = StObject.set(x, "rows", js.undefined)
    
    inline def setTitle(value: String): Self = StObject.set(x, "title", value.asInstanceOf[js.Any])
  }
}
