package typings.tabulatorTables.mod

import typings.tabulatorTables.anon.PickFilterfieldvalue
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsFiltering extends StObject {
  
  /** When using real time header filtering, Tabulator will wait 300 milliseconds after a keystroke before triggering the filter. You can customize this delay by using the headerFilterLiveFilterDelay table setup option. */
  var headerFilterLiveFilterDelay: js.UndefOr[Double] = js.undefined
  
  /** Array of filters to be applied on load. */
  var initialFilter: js.UndefOr[js.Array[Filter]] = js.undefined
  
  /** array of initial values for header filters. */
  var initialHeaderFilter: js.UndefOr[js.Array[PickFilterfieldvalue]] = js.undefined
}
object OptionsFiltering {
  
  inline def apply(): OptionsFiltering = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsFiltering]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsFiltering] (val x: Self) extends AnyVal {
    
    inline def setHeaderFilterLiveFilterDelay(value: Double): Self = StObject.set(x, "headerFilterLiveFilterDelay", value.asInstanceOf[js.Any])
    
    inline def setHeaderFilterLiveFilterDelayUndefined: Self = StObject.set(x, "headerFilterLiveFilterDelay", js.undefined)
    
    inline def setInitialFilter(value: js.Array[Filter]): Self = StObject.set(x, "initialFilter", value.asInstanceOf[js.Any])
    
    inline def setInitialFilterUndefined: Self = StObject.set(x, "initialFilter", js.undefined)
    
    inline def setInitialFilterVarargs(value: Filter*): Self = StObject.set(x, "initialFilter", js.Array(value*))
    
    inline def setInitialHeaderFilter(value: js.Array[PickFilterfieldvalue]): Self = StObject.set(x, "initialHeaderFilter", value.asInstanceOf[js.Any])
    
    inline def setInitialHeaderFilterUndefined: Self = StObject.set(x, "initialHeaderFilter", js.undefined)
    
    inline def setInitialHeaderFilterVarargs(value: PickFilterfieldvalue*): Self = StObject.set(x, "initialHeaderFilter", js.Array(value*))
  }
}
