package typings.tabulatorTables.mod

import org.scalajs.dom.HTMLElement
import typings.tabulatorTables.anon.CellClick
import typings.tabulatorTables.tabulatorTablesBooleans.`false`
import typings.tabulatorTables.tabulatorTablesStrings.blocking
import typings.tabulatorTables.tabulatorTablesStrings.highlight
import typings.tabulatorTables.tabulatorTablesStrings.manual
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsGeneral extends StObject {
  
  // Not listed in options--------------------
  /** Tabulator will automatically attempt to redraw the data contained in the table if the containing element for the table is resized. To disable this functionality, set the autoResize property to false. */
  var autoResize: js.UndefOr[Boolean] = js.undefined
  
  /**
    * The value to set in the cell after the user has finished editing the cell.
    */
  var editorEmptyValue: js.UndefOr[Any] = js.undefined
  
  /**
    * The function to determine if the value is empty.
    */
  var editorEmptyValueFunc: js.UndefOr[js.Function1[/* value */ Any, Boolean]] = js.undefined
  
  /** Footer  element to display for the table. */
  var footerElement: js.UndefOr[String | HTMLElement] = js.undefined
  
  /** Sets the height of the containing element, can be set to any valid height css value. If set to false (the default), the height of the table will resize to fit the table data. */
  var height: js.UndefOr[String | Double | `false`] = js.undefined
  
  /** Setting the invalidOptionWarnings option to false will disable console warning messages for invalid properties in the table constructor and column definition object. */
  var invalidOptionWarnings: js.UndefOr[Boolean] = js.undefined
  
  /** Keybinding configuration object. */
  var keybindings: js.UndefOr[`false` | KeyBinding] = js.undefined
  
  /** Can be set to any valid CSS value. By setting this you can allow your table to expand to fit the data, but not overflow its parent element. When there are too many rows to fit in the available space, the vertical scroll bar will be shown. This has the added benefit of improving load times on larger tables */
  var maxHeight: js.UndefOr[String | Double] = js.undefined
  
  /** With a variable table height you can set the minimum height of the table either defined in the min-height CSS property for the element or set it using the minHeight option in the table constructor, this can be set to any valid CSS value. */
  var minHeight: js.UndefOr[String | Double] = js.undefined
  
  /** placeholder element to display on empty table. */
  var placeholder: js.UndefOr[
    String | HTMLElement | (js.ThisFunction0[/* this */ Tabulator | TabulatorFull, String])
  ] = js.undefined
  
  var placeholderHeaderFilter: js.UndefOr[
    String | HTMLElement | (js.ThisFunction0[/* this */ Tabulator | TabulatorFull, String])
  ] = js.undefined
  
  /**
    * The reactivity systems allow Tabulator to watch arrays and objects passed into the table for changes and then automatically update the table.
    *
    * This approach means you no longer need to worry about calling a number of different functions on the table to make changes, you simply update the array or object you originally passed into the table and Tabulator will take care of the rest.
    *
    * You can enable reactive data by setting the reactiveData option to true in the table constructor, and then passing your data array to the data option.
    *
    * Once the table is built any changes to the array will automatically be replicated to the table without needing to call any functions on the table itself
    */
  var reactiveData: js.UndefOr[Boolean] = js.undefined
  
  var renderHorizontal: js.UndefOr[RenderMode] = js.undefined
  
  var renderVertical: js.UndefOr[RenderMode] = js.undefined
  
  /** Manually set the size of the virtual DOM buffer. */
  var renderVerticalBuffer: js.UndefOr[Boolean | Double] = js.undefined
  
  /**
    * Sometimes it can be useful to add a visual header to the start of a row.
    * The `rowHeader` option allows you to define a column definition for a stylized header column at the start of the row.
    *
    * This can be great for adding row number, movable row handles or row selections, and keeps the controls visually separated from the table data.
    */
  var rowHeader: js.UndefOr[Boolean | CellClick] = js.undefined
  
  var rowHeight: js.UndefOr[Double] = js.undefined
  
  var textDirection: js.UndefOr[TextDirection] = js.undefined
  
  /**
    * There are now three different validation modes available to customize the validation experience:
    *
    * blocking - if a user enters an invalid value while editing, they are blocked from leaving the cell until a valid value is entered (default)
    *
    * highlight - if a user enters an invalid value, then the edit will complete as usual and they are allowed to exit the cell but a highlight is applied to the cell using the tabulator-validation-fail class
    *
    * manual - no validation is automatically performed on edit, but it can be triggered by calling the validate function on the table or any Component Object
    */
  var validationMode: js.UndefOr[blocking | highlight | manual] = js.undefined
}
object OptionsGeneral {
  
  inline def apply(): OptionsGeneral = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsGeneral]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsGeneral] (val x: Self) extends AnyVal {
    
    inline def setAutoResize(value: Boolean): Self = StObject.set(x, "autoResize", value.asInstanceOf[js.Any])
    
    inline def setAutoResizeUndefined: Self = StObject.set(x, "autoResize", js.undefined)
    
    inline def setEditorEmptyValue(value: Any): Self = StObject.set(x, "editorEmptyValue", value.asInstanceOf[js.Any])
    
    inline def setEditorEmptyValueFunc(value: /* value */ Any => Boolean): Self = StObject.set(x, "editorEmptyValueFunc", js.Any.fromFunction1(value))
    
    inline def setEditorEmptyValueFuncUndefined: Self = StObject.set(x, "editorEmptyValueFunc", js.undefined)
    
    inline def setEditorEmptyValueUndefined: Self = StObject.set(x, "editorEmptyValue", js.undefined)
    
    inline def setFooterElement(value: String | HTMLElement): Self = StObject.set(x, "footerElement", value.asInstanceOf[js.Any])
    
    inline def setFooterElementUndefined: Self = StObject.set(x, "footerElement", js.undefined)
    
    inline def setHeight(value: String | Double | `false`): Self = StObject.set(x, "height", value.asInstanceOf[js.Any])
    
    inline def setHeightUndefined: Self = StObject.set(x, "height", js.undefined)
    
    inline def setInvalidOptionWarnings(value: Boolean): Self = StObject.set(x, "invalidOptionWarnings", value.asInstanceOf[js.Any])
    
    inline def setInvalidOptionWarningsUndefined: Self = StObject.set(x, "invalidOptionWarnings", js.undefined)
    
    inline def setKeybindings(value: `false` | KeyBinding): Self = StObject.set(x, "keybindings", value.asInstanceOf[js.Any])
    
    inline def setKeybindingsUndefined: Self = StObject.set(x, "keybindings", js.undefined)
    
    inline def setMaxHeight(value: String | Double): Self = StObject.set(x, "maxHeight", value.asInstanceOf[js.Any])
    
    inline def setMaxHeightUndefined: Self = StObject.set(x, "maxHeight", js.undefined)
    
    inline def setMinHeight(value: String | Double): Self = StObject.set(x, "minHeight", value.asInstanceOf[js.Any])
    
    inline def setMinHeightUndefined: Self = StObject.set(x, "minHeight", js.undefined)
    
    inline def setPlaceholder(value: String | HTMLElement | (js.ThisFunction0[/* this */ Tabulator | TabulatorFull, String])): Self = StObject.set(x, "placeholder", value.asInstanceOf[js.Any])
    
    inline def setPlaceholderHeaderFilter(value: String | HTMLElement | (js.ThisFunction0[/* this */ Tabulator | TabulatorFull, String])): Self = StObject.set(x, "placeholderHeaderFilter", value.asInstanceOf[js.Any])
    
    inline def setPlaceholderHeaderFilterUndefined: Self = StObject.set(x, "placeholderHeaderFilter", js.undefined)
    
    inline def setPlaceholderUndefined: Self = StObject.set(x, "placeholder", js.undefined)
    
    inline def setReactiveData(value: Boolean): Self = StObject.set(x, "reactiveData", value.asInstanceOf[js.Any])
    
    inline def setReactiveDataUndefined: Self = StObject.set(x, "reactiveData", js.undefined)
    
    inline def setRenderHorizontal(value: RenderMode): Self = StObject.set(x, "renderHorizontal", value.asInstanceOf[js.Any])
    
    inline def setRenderHorizontalUndefined: Self = StObject.set(x, "renderHorizontal", js.undefined)
    
    inline def setRenderVertical(value: RenderMode): Self = StObject.set(x, "renderVertical", value.asInstanceOf[js.Any])
    
    inline def setRenderVerticalBuffer(value: Boolean | Double): Self = StObject.set(x, "renderVerticalBuffer", value.asInstanceOf[js.Any])
    
    inline def setRenderVerticalBufferUndefined: Self = StObject.set(x, "renderVerticalBuffer", js.undefined)
    
    inline def setRenderVerticalUndefined: Self = StObject.set(x, "renderVertical", js.undefined)
    
    inline def setRowHeader(value: Boolean | CellClick): Self = StObject.set(x, "rowHeader", value.asInstanceOf[js.Any])
    
    inline def setRowHeaderUndefined: Self = StObject.set(x, "rowHeader", js.undefined)
    
    inline def setRowHeight(value: Double): Self = StObject.set(x, "rowHeight", value.asInstanceOf[js.Any])
    
    inline def setRowHeightUndefined: Self = StObject.set(x, "rowHeight", js.undefined)
    
    inline def setTextDirection(value: TextDirection): Self = StObject.set(x, "textDirection", value.asInstanceOf[js.Any])
    
    inline def setTextDirectionUndefined: Self = StObject.set(x, "textDirection", js.undefined)
    
    inline def setValidationMode(value: blocking | highlight | manual): Self = StObject.set(x, "validationMode", value.asInstanceOf[js.Any])
    
    inline def setValidationModeUndefined: Self = StObject.set(x, "validationMode", js.undefined)
  }
}
