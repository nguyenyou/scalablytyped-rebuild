package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsSpreadsheet extends StObject {
  
  /**
    * Enables the spreadsheet mode on the table.
    *
    * The SpreadsheetModule must be installed to use this functionality.
    */
  var spreadsheet: js.UndefOr[Boolean] = js.undefined
  
  var spreadsheetColumnDefinition: js.UndefOr[typings.tabulatorTables.anon.Editor] = js.undefined
  
  var spreadsheetColumns: js.UndefOr[Double] = js.undefined
  
  var spreadsheetOutputFull: js.UndefOr[Boolean] = js.undefined
  
  var spreadsheetRows: js.UndefOr[Double] = js.undefined
  
  var spreadsheetSheetTabs: js.UndefOr[Boolean] = js.undefined
  
  var spreadsheetSheets: js.UndefOr[js.Array[SpreadsheetSheet]] = js.undefined
}
object OptionsSpreadsheet {
  
  inline def apply(): OptionsSpreadsheet = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsSpreadsheet]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsSpreadsheet] (val x: Self) extends AnyVal {
    
    inline def setSpreadsheet(value: Boolean): Self = StObject.set(x, "spreadsheet", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetColumnDefinition(value: typings.tabulatorTables.anon.Editor): Self = StObject.set(x, "spreadsheetColumnDefinition", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetColumnDefinitionUndefined: Self = StObject.set(x, "spreadsheetColumnDefinition", js.undefined)
    
    inline def setSpreadsheetColumns(value: Double): Self = StObject.set(x, "spreadsheetColumns", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetColumnsUndefined: Self = StObject.set(x, "spreadsheetColumns", js.undefined)
    
    inline def setSpreadsheetOutputFull(value: Boolean): Self = StObject.set(x, "spreadsheetOutputFull", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetOutputFullUndefined: Self = StObject.set(x, "spreadsheetOutputFull", js.undefined)
    
    inline def setSpreadsheetRows(value: Double): Self = StObject.set(x, "spreadsheetRows", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetRowsUndefined: Self = StObject.set(x, "spreadsheetRows", js.undefined)
    
    inline def setSpreadsheetSheetTabs(value: Boolean): Self = StObject.set(x, "spreadsheetSheetTabs", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetSheetTabsUndefined: Self = StObject.set(x, "spreadsheetSheetTabs", js.undefined)
    
    inline def setSpreadsheetSheets(value: js.Array[SpreadsheetSheet]): Self = StObject.set(x, "spreadsheetSheets", value.asInstanceOf[js.Any])
    
    inline def setSpreadsheetSheetsUndefined: Self = StObject.set(x, "spreadsheetSheets", js.undefined)
    
    inline def setSpreadsheetSheetsVarargs(value: SpreadsheetSheet*): Self = StObject.set(x, "spreadsheetSheets", js.Array(value*))
    
    inline def setSpreadsheetUndefined: Self = StObject.set(x, "spreadsheet", js.undefined)
  }
}
