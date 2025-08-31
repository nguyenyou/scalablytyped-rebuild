package typings.tabulatorTables.mod

import org.scalajs.dom.HTMLElement
import typings.tabulatorTables.tabulatorTablesBooleans.`false`
import typings.tabulatorTables.tabulatorTablesStrings.add
import typings.tabulatorTables.tabulatorTablesStrings.bottom
import typings.tabulatorTables.tabulatorTablesStrings.click
import typings.tabulatorTables.tabulatorTablesStrings.dblclick
import typings.tabulatorTables.tabulatorTablesStrings.delete
import typings.tabulatorTables.tabulatorTablesStrings.focus
import typings.tabulatorTables.tabulatorTablesStrings.highlight
import typings.tabulatorTables.tabulatorTablesStrings.insert
import typings.tabulatorTables.tabulatorTablesStrings.replace
import typings.tabulatorTables.tabulatorTablesStrings.top
import typings.tabulatorTables.tabulatorTablesStrings.update
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsRows extends StObject {
  
  /** The position in the table for new rows to be added, "bottom" or "top". */
  var addRowPos: js.UndefOr[bottom | top] = js.undefined
  
  /**
    * The editTriggerEvent option lets you choose which type of interaction event will trigger an edit on a cell.
    *
    * @example
    * var table = new Tabulator("#example-table", {
    *     editTriggerEvent:"dblclick", // trigger edit on double click
    * });
    *
    * This option can take one of three values:
    *
    * - focus - trigger edit when the cell has focus (default)
    * - click - trigger edit on single click on cell
    * - dblclick - trigger edit on double click on cell
    *
    * This option does not affect navigation behavior, cells edits will still be triggered when they are navigated to
    * through arrow keys or tabs.
    */
  var editTriggerEvent: js.UndefOr[click | dblclick | focus] = js.undefined
  
  /** Freeze rows of data */
  var frozenRows: js.UndefOr[Double | js.Array[String] | (js.Function1[/* row */ RowComponent, Boolean])] = js.undefined
  
  var frozenRowsField: js.UndefOr[String] = js.undefined
  
  /** To allow the user to move rows up and down the table, set the movableRows parameter in the options: */
  var movableRows: js.UndefOr[Boolean] = js.undefined
  
  var movableRowsConnectedElements: js.UndefOr[String | HTMLElement] = js.undefined
  
  /** Tabulator also allows you to move rows between tables. To enable this you should supply either a valid CSS selector string a DOM node for the table or the Tabulator object for the table to the movableRowsConnectedTables option. if you want to connect to multiple tables then you can pass in an array of values to this option. */
  var movableRowsConnectedTables: js.UndefOr[String | (js.Array[HTMLElement | String]) | HTMLElement] = js.undefined
  
  /**
    * The movableRowsReceiver option should be set on the receiving tables, and sets the action that should be taken when the row is dropped into the table.
    * There are several inbuilt receiver functions:
    *
    * - insert - inserts row next to the row it was dropped on, if not dropped on a row it is added to the table (default)
    * - add - adds row to the table
    * - update - updates the row it is dropped on with the sent rows data
    * - replace - replaces the row it is dropped on with the sent row
    */
  var movableRowsReceiver: js.UndefOr[
    insert | add | update | replace | (js.Function3[/* fromRow */ RowComponent, /* toRow */ RowComponent, /* fromTable */ Tabulator, Any])
  ] = js.undefined
  
  /**
    * The movableRowsSender option should be set on the sending table, and sets the action that should be taken after the row has been successfully dropped into the receiving table.
    * There are several inbuilt sender functions:
    *
    * - false - do nothing(default)
    * - delete - deletes the row from the table
    * You can also pass a callback to the movableRowsSender option for custom sender functionality
    */
  var movableRowsSender: js.UndefOr[
    `false` | delete | (js.Function3[/* fromRow */ RowComponent, /* toRow */ RowComponent, /* toTable */ Tabulator, Any])
  ] = js.undefined
  
  /**
    * Allows the user to control the height of columns in the table by dragging the border of the column.
    * These guides will only appear if the `resizableRows` option is enabled.
    */
  var resizableColumnGuide: js.UndefOr[Boolean] = js.undefined
  
  /**
    * Allows the user to control the height of rows in the table by dragging the bottom border of the row.
    * These guides will only appear on columns with the `resizable` option enabled in their column definition.
    */
  var resizableRowGuide: js.UndefOr[Boolean] = js.undefined
  
  /** You can allow the user to manually resize rows by dragging the top or bottom border of a row. To enable this functionality, set the resizableRows property to true. */
  var resizableRows: js.UndefOr[Boolean] = js.undefined
  
  /**
    * Tabulator also allows you to define a row level formatter using the rowFormatter option. this lets you alter each row of the table based on the data it contains.
    * The function accepts one argument, the RowComponent for the row being formatted.
    */
  var rowFormatter: js.UndefOr[js.Function1[/* row */ RowComponent, Any]] = js.undefined
  
  /** When copying to the clipboard you may want to apply a different formatter may want to apply a different formatter from the one usually used to format the row. You can now do this using the rowFormatterClipboard table option, which takes the same inputs as the standard rowFormatter property. Passing a value of false into the formatter prevent the default row formatter from being run when the table is copied to the clipboard. */
  var rowFormatterClipboard: js.UndefOr[`false` | (js.Function1[/* row */ RowComponent, Any])] = js.undefined
  
  /** When the getHtml function is called you may want to apply a different formatter may want to apply a different formatter from the one usually used to format the row */
  var rowFormatterHtmlOutput: js.UndefOr[`false` | (js.Function1[/* row */ RowComponent, Any])] = js.undefined
  
  /** When printing you may want to apply a different formatter may want to apply a different formatter from the one usually used to format the row. */
  var rowFormatterPrint: js.UndefOr[`false` | (js.Function1[/* row */ RowComponent, Any])] = js.undefined
  
  /**
    * The default option for triggering a ScrollTo on a visible element can be set using the scrollToRowIfVisible option. It can take a boolean value:
    *
    * true - scroll to row, even if it is visible (default)
    * false - scroll to row, unless it is currently visible, then don't move
    */
  var scrollToRowIfVisible: js.UndefOr[Boolean] = js.undefined
  
  /**
    * The default ScrollTo position can be set using the scrollToRowPosition option. It can take one of four possible values:
    *
    * top - position row with its top edge at the top of the table (default)
    * center - position row with its top edge in the center of the table
    * bottom - position row with its bottom edge at the bottom of the table
    * nearest - position row on the edge of the table it is closest to
    */
  var scrollToRowPosition: js.UndefOr[ScrollToRowPosition] = js.undefined
  
  /**
    * The selectableRange option can take one of a several values:
    *
    * - false - range selection is disabled
    * - true - range selection is enabled, and you can add as many ranges as you want
    * - integer - any integer value, this sets the maximum number of ranges that can be selected (when the maximum
    *           number of ranges is exceeded, the first selected range will be deselected to allow the next range to be selected).
    */
  var selectableRange: js.UndefOr[Boolean | Double] = js.undefined
  
  /**
    * If you want the user to be able to clear the values for all cells in the active range by pressing the backspace
    * or delete keys, then you can enable this behavior using the selectableRangeClearCells option:
    *
    * @example
    * var table = new Tabulator("#example-table", {
    *     selectableRangeClearCells:true,
    * });
    */
  var selectableRangeClearCells: js.UndefOr[Boolean] = js.undefined
  
  /**
    * By default the value of each cell in the range is set to undefined when this option is enabled and the user
    * presses the backspace or delete keys. You can change the value the cells are set to using the
    * selectableRangeClearCellsValue option
    *
    * @example
    * var table = new Tabulator("#example-table", {
    *     selectableRangeClearCellsValue: "", //clear cells by setting value to an empty string
    * });
    */
  var selectableRangeClearCellsValue: js.UndefOr[Any] = js.undefined
  
  /**
    * By default you can only select ranges by selecting cells on the table. If you would like to allow the user to
    * select all cells in a column by clicking on the column header, then you can set the selectableRangeColumns option to true
    */
  var selectableRangeColumns: js.UndefOr[Boolean] = js.undefined
  
  /**
    * By default you can only select ranges by selecting cells on the table. If you would like to allow the user to
    * select all cells in row by clicking on the row header, then you can set the selectableRangeColumns option to true
    */
  var selectableRangeRows: js.UndefOr[Boolean] = js.undefined
  
  /**
    * The selectableRows option can take one of a several values:
    *
    * - false - selectable rows are disabled
    * - true - selectable rows are enabled, and you can select as many as you want
    * - integer - any integer value, this sets the maximum number of rows that can be selected (when the maximum number of selected rows is exceeded, the first selected row will be deselected to allow the next row to be selected).
    * - "highlight" (default) - rows have the same hover stylings as selectable rows but do not change state when clicked. This is great for when you want to show that a row is clickable but don't want it to be selectable.
    */
  var selectableRows: js.UndefOr[Boolean | Double | highlight] = js.undefined
  
  /** You many want to exclude certain rows from being selected. The selectableRowsCheck options allows you to pass a function to check if the current row should be selectable, returning true will allow row selection, false will result in nothing happening. The function should accept a RowComponent as its first argument. */
  var selectableRowsCheck: js.UndefOr[js.Function1[/* row */ RowComponent, Boolean]] = js.undefined
  
  /**
    * By default Tabulator will maintain selected rows when the table is filtered, sorted or paginated (but NOT when
    * the setData function is used). If you want the selected rows to be cleared whenever the table view is updated
    * then set the selectableRowsPersistence option to false.
    *
    * @example
    * var table = new Tabulator("#example-table", {
    *     selectableRows: true,
    *     selectableRowsPersistence: false, // disable selection persistence
    * });
    */
  var selectableRowsPersistence: js.UndefOr[Boolean] = js.undefined
  
  /**
    * By default you can select a range of rows by holding down the shift key and click dragging over a number of rows
    * to toggle the selected state state of all rows the cursor passes over.
    *
    * If you would prefer to select a range of row by clicking on the first row then holding down shift and clicking
    * on the end row then you can achieve this by setting the selectableRowsRangeMode to click.
    *
    * @example
    * var table = new Tabulator("#example-table", {
    *     selectableRowsRangeMode:"click",
    * });
    */
  var selectableRowsRangeMode: js.UndefOr[click] = js.undefined
  
  /**
    * By default, row selection works on a rolling basis, if you set the selectableRows option to a numeric value then
    * when you select past this number of rows, the first row to be selected will be deselected. If you want to
    * disable this behavior and instead prevent selection of new rows once the limit is reached you can set the
    * selectableRowsRollingSelection option to false.
    *
    * @example
    * var table = new Tabulator("#example-table", {
    *     selectableRows: 5,
    *     selectableRowsRollingSelection:false, // disable rolling selection
    * });
    */
  var selectableRowsRollingSelection: js.UndefOr[Boolean] = js.undefined
  
  /** Allows you to specify the behavior when the user tabs from the last editable cell on the last row of the table. */
  var tabEndNewRow: js.UndefOr[Boolean | JSONRecord | (js.Function1[/* row */ RowComponent, Any])] = js.undefined
}
object OptionsRows {
  
  inline def apply(): OptionsRows = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsRows]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsRows] (val x: Self) extends AnyVal {
    
    inline def setAddRowPos(value: bottom | top): Self = StObject.set(x, "addRowPos", value.asInstanceOf[js.Any])
    
    inline def setAddRowPosUndefined: Self = StObject.set(x, "addRowPos", js.undefined)
    
    inline def setEditTriggerEvent(value: click | dblclick | focus): Self = StObject.set(x, "editTriggerEvent", value.asInstanceOf[js.Any])
    
    inline def setEditTriggerEventUndefined: Self = StObject.set(x, "editTriggerEvent", js.undefined)
    
    inline def setFrozenRows(value: Double | js.Array[String] | (js.Function1[/* row */ RowComponent, Boolean])): Self = StObject.set(x, "frozenRows", value.asInstanceOf[js.Any])
    
    inline def setFrozenRowsField(value: String): Self = StObject.set(x, "frozenRowsField", value.asInstanceOf[js.Any])
    
    inline def setFrozenRowsFieldUndefined: Self = StObject.set(x, "frozenRowsField", js.undefined)
    
    inline def setFrozenRowsFunction1(value: /* row */ RowComponent => Boolean): Self = StObject.set(x, "frozenRows", js.Any.fromFunction1(value))
    
    inline def setFrozenRowsUndefined: Self = StObject.set(x, "frozenRows", js.undefined)
    
    inline def setFrozenRowsVarargs(value: String*): Self = StObject.set(x, "frozenRows", js.Array(value*))
    
    inline def setMovableRows(value: Boolean): Self = StObject.set(x, "movableRows", value.asInstanceOf[js.Any])
    
    inline def setMovableRowsConnectedElements(value: String | HTMLElement): Self = StObject.set(x, "movableRowsConnectedElements", value.asInstanceOf[js.Any])
    
    inline def setMovableRowsConnectedElementsUndefined: Self = StObject.set(x, "movableRowsConnectedElements", js.undefined)
    
    inline def setMovableRowsConnectedTables(value: String | (js.Array[HTMLElement | String]) | HTMLElement): Self = StObject.set(x, "movableRowsConnectedTables", value.asInstanceOf[js.Any])
    
    inline def setMovableRowsConnectedTablesUndefined: Self = StObject.set(x, "movableRowsConnectedTables", js.undefined)
    
    inline def setMovableRowsConnectedTablesVarargs(value: (HTMLElement | String)*): Self = StObject.set(x, "movableRowsConnectedTables", js.Array(value*))
    
    inline def setMovableRowsReceiver(
      value: insert | add | update | replace | (js.Function3[/* fromRow */ RowComponent, /* toRow */ RowComponent, /* fromTable */ Tabulator, Any])
    ): Self = StObject.set(x, "movableRowsReceiver", value.asInstanceOf[js.Any])
    
    inline def setMovableRowsReceiverFunction3(value: (/* fromRow */ RowComponent, /* toRow */ RowComponent, /* fromTable */ Tabulator) => Any): Self = StObject.set(x, "movableRowsReceiver", js.Any.fromFunction3(value))
    
    inline def setMovableRowsReceiverUndefined: Self = StObject.set(x, "movableRowsReceiver", js.undefined)
    
    inline def setMovableRowsSender(
      value: `false` | delete | (js.Function3[/* fromRow */ RowComponent, /* toRow */ RowComponent, /* toTable */ Tabulator, Any])
    ): Self = StObject.set(x, "movableRowsSender", value.asInstanceOf[js.Any])
    
    inline def setMovableRowsSenderFunction3(value: (/* fromRow */ RowComponent, /* toRow */ RowComponent, /* toTable */ Tabulator) => Any): Self = StObject.set(x, "movableRowsSender", js.Any.fromFunction3(value))
    
    inline def setMovableRowsSenderUndefined: Self = StObject.set(x, "movableRowsSender", js.undefined)
    
    inline def setMovableRowsUndefined: Self = StObject.set(x, "movableRows", js.undefined)
    
    inline def setResizableColumnGuide(value: Boolean): Self = StObject.set(x, "resizableColumnGuide", value.asInstanceOf[js.Any])
    
    inline def setResizableColumnGuideUndefined: Self = StObject.set(x, "resizableColumnGuide", js.undefined)
    
    inline def setResizableRowGuide(value: Boolean): Self = StObject.set(x, "resizableRowGuide", value.asInstanceOf[js.Any])
    
    inline def setResizableRowGuideUndefined: Self = StObject.set(x, "resizableRowGuide", js.undefined)
    
    inline def setResizableRows(value: Boolean): Self = StObject.set(x, "resizableRows", value.asInstanceOf[js.Any])
    
    inline def setResizableRowsUndefined: Self = StObject.set(x, "resizableRows", js.undefined)
    
    inline def setRowFormatter(value: /* row */ RowComponent => Any): Self = StObject.set(x, "rowFormatter", js.Any.fromFunction1(value))
    
    inline def setRowFormatterClipboard(value: `false` | (js.Function1[/* row */ RowComponent, Any])): Self = StObject.set(x, "rowFormatterClipboard", value.asInstanceOf[js.Any])
    
    inline def setRowFormatterClipboardFunction1(value: /* row */ RowComponent => Any): Self = StObject.set(x, "rowFormatterClipboard", js.Any.fromFunction1(value))
    
    inline def setRowFormatterClipboardUndefined: Self = StObject.set(x, "rowFormatterClipboard", js.undefined)
    
    inline def setRowFormatterHtmlOutput(value: `false` | (js.Function1[/* row */ RowComponent, Any])): Self = StObject.set(x, "rowFormatterHtmlOutput", value.asInstanceOf[js.Any])
    
    inline def setRowFormatterHtmlOutputFunction1(value: /* row */ RowComponent => Any): Self = StObject.set(x, "rowFormatterHtmlOutput", js.Any.fromFunction1(value))
    
    inline def setRowFormatterHtmlOutputUndefined: Self = StObject.set(x, "rowFormatterHtmlOutput", js.undefined)
    
    inline def setRowFormatterPrint(value: `false` | (js.Function1[/* row */ RowComponent, Any])): Self = StObject.set(x, "rowFormatterPrint", value.asInstanceOf[js.Any])
    
    inline def setRowFormatterPrintFunction1(value: /* row */ RowComponent => Any): Self = StObject.set(x, "rowFormatterPrint", js.Any.fromFunction1(value))
    
    inline def setRowFormatterPrintUndefined: Self = StObject.set(x, "rowFormatterPrint", js.undefined)
    
    inline def setRowFormatterUndefined: Self = StObject.set(x, "rowFormatter", js.undefined)
    
    inline def setScrollToRowIfVisible(value: Boolean): Self = StObject.set(x, "scrollToRowIfVisible", value.asInstanceOf[js.Any])
    
    inline def setScrollToRowIfVisibleUndefined: Self = StObject.set(x, "scrollToRowIfVisible", js.undefined)
    
    inline def setScrollToRowPosition(value: ScrollToRowPosition): Self = StObject.set(x, "scrollToRowPosition", value.asInstanceOf[js.Any])
    
    inline def setScrollToRowPositionUndefined: Self = StObject.set(x, "scrollToRowPosition", js.undefined)
    
    inline def setSelectableRange(value: Boolean | Double): Self = StObject.set(x, "selectableRange", value.asInstanceOf[js.Any])
    
    inline def setSelectableRangeClearCells(value: Boolean): Self = StObject.set(x, "selectableRangeClearCells", value.asInstanceOf[js.Any])
    
    inline def setSelectableRangeClearCellsUndefined: Self = StObject.set(x, "selectableRangeClearCells", js.undefined)
    
    inline def setSelectableRangeClearCellsValue(value: Any): Self = StObject.set(x, "selectableRangeClearCellsValue", value.asInstanceOf[js.Any])
    
    inline def setSelectableRangeClearCellsValueUndefined: Self = StObject.set(x, "selectableRangeClearCellsValue", js.undefined)
    
    inline def setSelectableRangeColumns(value: Boolean): Self = StObject.set(x, "selectableRangeColumns", value.asInstanceOf[js.Any])
    
    inline def setSelectableRangeColumnsUndefined: Self = StObject.set(x, "selectableRangeColumns", js.undefined)
    
    inline def setSelectableRangeRows(value: Boolean): Self = StObject.set(x, "selectableRangeRows", value.asInstanceOf[js.Any])
    
    inline def setSelectableRangeRowsUndefined: Self = StObject.set(x, "selectableRangeRows", js.undefined)
    
    inline def setSelectableRangeUndefined: Self = StObject.set(x, "selectableRange", js.undefined)
    
    inline def setSelectableRows(value: Boolean | Double | highlight): Self = StObject.set(x, "selectableRows", value.asInstanceOf[js.Any])
    
    inline def setSelectableRowsCheck(value: /* row */ RowComponent => Boolean): Self = StObject.set(x, "selectableRowsCheck", js.Any.fromFunction1(value))
    
    inline def setSelectableRowsCheckUndefined: Self = StObject.set(x, "selectableRowsCheck", js.undefined)
    
    inline def setSelectableRowsPersistence(value: Boolean): Self = StObject.set(x, "selectableRowsPersistence", value.asInstanceOf[js.Any])
    
    inline def setSelectableRowsPersistenceUndefined: Self = StObject.set(x, "selectableRowsPersistence", js.undefined)
    
    inline def setSelectableRowsRangeMode(value: click): Self = StObject.set(x, "selectableRowsRangeMode", value.asInstanceOf[js.Any])
    
    inline def setSelectableRowsRangeModeUndefined: Self = StObject.set(x, "selectableRowsRangeMode", js.undefined)
    
    inline def setSelectableRowsRollingSelection(value: Boolean): Self = StObject.set(x, "selectableRowsRollingSelection", value.asInstanceOf[js.Any])
    
    inline def setSelectableRowsRollingSelectionUndefined: Self = StObject.set(x, "selectableRowsRollingSelection", js.undefined)
    
    inline def setSelectableRowsUndefined: Self = StObject.set(x, "selectableRows", js.undefined)
    
    inline def setTabEndNewRow(value: Boolean | JSONRecord | (js.Function1[/* row */ RowComponent, Any])): Self = StObject.set(x, "tabEndNewRow", value.asInstanceOf[js.Any])
    
    inline def setTabEndNewRowFunction1(value: /* row */ RowComponent => Any): Self = StObject.set(x, "tabEndNewRow", js.Any.fromFunction1(value))
    
    inline def setTabEndNewRowUndefined: Self = StObject.set(x, "tabEndNewRow", js.undefined)
  }
}
