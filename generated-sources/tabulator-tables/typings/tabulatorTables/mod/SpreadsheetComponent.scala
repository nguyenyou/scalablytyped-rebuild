package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait SpreadsheetComponent extends StObject {
  
  def active(): Unit
  
  def clear(): Unit
  
  def getData(): js.Array[js.Array[Any]]
  
  def getDefinition(): SpreadsheetSheet
  
  def getKey(): String
  
  def getTitle(): String
  
  def remove(): Unit
  
  def setColumns(columns: Double): Unit
  
  def setData(data: js.Array[js.Array[Any]]): Unit
  
  def setRows(rows: Double): Unit
  
  def setTitle(title: String): Unit
}
object SpreadsheetComponent {
  
  inline def apply(
    active: () => Unit,
    clear: () => Unit,
    getData: () => js.Array[js.Array[Any]],
    getDefinition: () => SpreadsheetSheet,
    getKey: () => String,
    getTitle: () => String,
    remove: () => Unit,
    setColumns: Double => Unit,
    setData: js.Array[js.Array[Any]] => Unit,
    setRows: Double => Unit,
    setTitle: String => Unit
  ): SpreadsheetComponent = {
    val __obj = js.Dynamic.literal(active = js.Any.fromFunction0(active), clear = js.Any.fromFunction0(clear), getData = js.Any.fromFunction0(getData), getDefinition = js.Any.fromFunction0(getDefinition), getKey = js.Any.fromFunction0(getKey), getTitle = js.Any.fromFunction0(getTitle), remove = js.Any.fromFunction0(remove), setColumns = js.Any.fromFunction1(setColumns), setData = js.Any.fromFunction1(setData), setRows = js.Any.fromFunction1(setRows), setTitle = js.Any.fromFunction1(setTitle))
    __obj.asInstanceOf[SpreadsheetComponent]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: SpreadsheetComponent] (val x: Self) extends AnyVal {
    
    inline def setActive(value: () => Unit): Self = StObject.set(x, "active", js.Any.fromFunction0(value))
    
    inline def setClear(value: () => Unit): Self = StObject.set(x, "clear", js.Any.fromFunction0(value))
    
    inline def setGetData(value: () => js.Array[js.Array[Any]]): Self = StObject.set(x, "getData", js.Any.fromFunction0(value))
    
    inline def setGetDefinition(value: () => SpreadsheetSheet): Self = StObject.set(x, "getDefinition", js.Any.fromFunction0(value))
    
    inline def setGetKey(value: () => String): Self = StObject.set(x, "getKey", js.Any.fromFunction0(value))
    
    inline def setGetTitle(value: () => String): Self = StObject.set(x, "getTitle", js.Any.fromFunction0(value))
    
    inline def setRemove(value: () => Unit): Self = StObject.set(x, "remove", js.Any.fromFunction0(value))
    
    inline def setSetColumns(value: Double => Unit): Self = StObject.set(x, "setColumns", js.Any.fromFunction1(value))
    
    inline def setSetData(value: js.Array[js.Array[Any]] => Unit): Self = StObject.set(x, "setData", js.Any.fromFunction1(value))
    
    inline def setSetRows(value: Double => Unit): Self = StObject.set(x, "setRows", js.Any.fromFunction1(value))
    
    inline def setSetTitle(value: String => Unit): Self = StObject.set(x, "setTitle", js.Any.fromFunction1(value))
  }
}
