package typings.tabulatorTables.mod

import typings.tabulatorTables.tabulatorTablesBooleans.`false`
import typings.tabulatorTables.tabulatorTablesStrings.arrow
import typings.tabulatorTables.tabulatorTablesStrings.header
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsRowGrouping extends StObject {
  
  /** String/function to select field to group rows by */
  var groupBy: js.UndefOr[GroupArg] = js.undefined
  
  /** show/hide column calculations when group is closed. */
  var groupClosedShowCalcs: js.UndefOr[Boolean] = js.undefined
  
  /** You can use the setGroupHeader function to change the header generation function for each group. This function has one argument and takes the same values as passed to the groupHeader setup option. */
  var groupHeader: js.UndefOr[
    (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, String]) | (js.Array[js.Function3[/* value */ Any, /* count */ Double, /* data */ Any, String]])
  ] = js.undefined
  
  /** When printing you may want to apply a different group header from the one usually used in the table. You can now do this using the groupHeaderPrint table option, which takes the same inputs as the standard groupHeader property. */
  var groupHeaderPrint: js.UndefOr[
    (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, String]) | (js.Array[js.Function3[/* value */ Any, /* count */ Double, /* data */ Any, String]])
  ] = js.undefined
  
  /**
    * You can set the default open state of groups using the groupStartOpen property* * This can take one of three possible values:
    *
    * true - all groups start open (default value)
    * false - all groups start closed
    * function() - a callback to decide if a group should start open or closed
    * Group Open Function
    * If you want to decide on a group by group basis which should start open or closed then you can pass a function to the groupStartOpen property. This should return true if the group should start open or false if the group should start closed.
    */
  var groupStartOpen: js.UndefOr[
    Boolean | (js.Array[
      Boolean | (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, Boolean])
    ]) | (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, Boolean])
  ] = js.undefined
  
  /**
    * By default Tabulator allows users to toggle a group open or closed by clicking on the arrow icon in the left of the group header. If you would prefer a different behavior you can use the groupToggleElement option to choose a different option:* * The option can take one of three values:
    * arrow - toggle group on arrow element click
    * header - toggle group on click anywhere on the group header element
    * false - prevent clicking anywhere in the group toggling the group
    */
  var groupToggleElement: js.UndefOr[arrow | header | `false`] = js.undefined
  
  var groupUpdateOnCellEdit: js.UndefOr[Boolean] = js.undefined
  
  /**
    * By default Tabulator will create groups for rows based on the values contained in the row data. if you want to explicitly define which field values groups should be created for at each level, you can use the groupValues option.
    *
    * This option takes an array of value arrays, each item in the first array should be a list of acceptable field values for groups at that level
    */
  var groupValues: js.UndefOr[GroupValuesArg] = js.undefined
}
object OptionsRowGrouping {
  
  inline def apply(): OptionsRowGrouping = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsRowGrouping]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsRowGrouping] (val x: Self) extends AnyVal {
    
    inline def setGroupBy(value: GroupArg): Self = StObject.set(x, "groupBy", value.asInstanceOf[js.Any])
    
    inline def setGroupByFunction1(value: /* data */ Any => Any): Self = StObject.set(x, "groupBy", js.Any.fromFunction1(value))
    
    inline def setGroupByUndefined: Self = StObject.set(x, "groupBy", js.undefined)
    
    inline def setGroupByVarargs(value: ((js.Function1[/* data */ Any, Any]) | String)*): Self = StObject.set(x, "groupBy", js.Array(value*))
    
    inline def setGroupClosedShowCalcs(value: Boolean): Self = StObject.set(x, "groupClosedShowCalcs", value.asInstanceOf[js.Any])
    
    inline def setGroupClosedShowCalcsUndefined: Self = StObject.set(x, "groupClosedShowCalcs", js.undefined)
    
    inline def setGroupHeader(
      value: (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, String]) | (js.Array[js.Function3[/* value */ Any, /* count */ Double, /* data */ Any, String]])
    ): Self = StObject.set(x, "groupHeader", value.asInstanceOf[js.Any])
    
    inline def setGroupHeaderFunction4(value: (/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent) => String): Self = StObject.set(x, "groupHeader", js.Any.fromFunction4(value))
    
    inline def setGroupHeaderPrint(
      value: (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, String]) | (js.Array[js.Function3[/* value */ Any, /* count */ Double, /* data */ Any, String]])
    ): Self = StObject.set(x, "groupHeaderPrint", value.asInstanceOf[js.Any])
    
    inline def setGroupHeaderPrintFunction4(value: (/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent) => String): Self = StObject.set(x, "groupHeaderPrint", js.Any.fromFunction4(value))
    
    inline def setGroupHeaderPrintUndefined: Self = StObject.set(x, "groupHeaderPrint", js.undefined)
    
    inline def setGroupHeaderPrintVarargs(value: (js.Function3[/* value */ Any, /* count */ Double, /* data */ Any, String])*): Self = StObject.set(x, "groupHeaderPrint", js.Array(value*))
    
    inline def setGroupHeaderUndefined: Self = StObject.set(x, "groupHeader", js.undefined)
    
    inline def setGroupHeaderVarargs(value: (js.Function3[/* value */ Any, /* count */ Double, /* data */ Any, String])*): Self = StObject.set(x, "groupHeader", js.Array(value*))
    
    inline def setGroupStartOpen(
      value: Boolean | (js.Array[
          Boolean | (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, Boolean])
        ]) | (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, Boolean])
    ): Self = StObject.set(x, "groupStartOpen", value.asInstanceOf[js.Any])
    
    inline def setGroupStartOpenFunction4(
      value: (/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent) => Boolean
    ): Self = StObject.set(x, "groupStartOpen", js.Any.fromFunction4(value))
    
    inline def setGroupStartOpenUndefined: Self = StObject.set(x, "groupStartOpen", js.undefined)
    
    inline def setGroupStartOpenVarargs(
      value: (Boolean | (js.Function4[/* value */ Any, /* count */ Double, /* data */ Any, /* group */ GroupComponent, Boolean]))*
    ): Self = StObject.set(x, "groupStartOpen", js.Array(value*))
    
    inline def setGroupToggleElement(value: arrow | header | `false`): Self = StObject.set(x, "groupToggleElement", value.asInstanceOf[js.Any])
    
    inline def setGroupToggleElementUndefined: Self = StObject.set(x, "groupToggleElement", js.undefined)
    
    inline def setGroupUpdateOnCellEdit(value: Boolean): Self = StObject.set(x, "groupUpdateOnCellEdit", value.asInstanceOf[js.Any])
    
    inline def setGroupUpdateOnCellEditUndefined: Self = StObject.set(x, "groupUpdateOnCellEdit", js.undefined)
    
    inline def setGroupValues(value: GroupValuesArg): Self = StObject.set(x, "groupValues", value.asInstanceOf[js.Any])
    
    inline def setGroupValuesUndefined: Self = StObject.set(x, "groupValues", js.undefined)
    
    inline def setGroupValuesVarargs(value: js.Array[Any]*): Self = StObject.set(x, "groupValues", js.Array(value*))
  }
}
