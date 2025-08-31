package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait ToggleSwitchParams
  extends StObject
     with _FormatterParams {
  
  var clickable: js.UndefOr[Boolean] = js.undefined
  
  var max: js.UndefOr[Double] = js.undefined
  
  var offColor: js.UndefOr[String] = js.undefined
  
  var offValue: js.UndefOr[String | Double] = js.undefined
  
  var onColor: js.UndefOr[String] = js.undefined
  
  var onTruthy: js.UndefOr[Boolean] = js.undefined
  
  var onValue: js.UndefOr[String | Double] = js.undefined
  
  var size: js.UndefOr[Double] = js.undefined
}
object ToggleSwitchParams {
  
  inline def apply(): ToggleSwitchParams = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[ToggleSwitchParams]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: ToggleSwitchParams] (val x: Self) extends AnyVal {
    
    inline def setClickable(value: Boolean): Self = StObject.set(x, "clickable", value.asInstanceOf[js.Any])
    
    inline def setClickableUndefined: Self = StObject.set(x, "clickable", js.undefined)
    
    inline def setMax(value: Double): Self = StObject.set(x, "max", value.asInstanceOf[js.Any])
    
    inline def setMaxUndefined: Self = StObject.set(x, "max", js.undefined)
    
    inline def setOffColor(value: String): Self = StObject.set(x, "offColor", value.asInstanceOf[js.Any])
    
    inline def setOffColorUndefined: Self = StObject.set(x, "offColor", js.undefined)
    
    inline def setOffValue(value: String | Double): Self = StObject.set(x, "offValue", value.asInstanceOf[js.Any])
    
    inline def setOffValueUndefined: Self = StObject.set(x, "offValue", js.undefined)
    
    inline def setOnColor(value: String): Self = StObject.set(x, "onColor", value.asInstanceOf[js.Any])
    
    inline def setOnColorUndefined: Self = StObject.set(x, "onColor", js.undefined)
    
    inline def setOnTruthy(value: Boolean): Self = StObject.set(x, "onTruthy", value.asInstanceOf[js.Any])
    
    inline def setOnTruthyUndefined: Self = StObject.set(x, "onTruthy", js.undefined)
    
    inline def setOnValue(value: String | Double): Self = StObject.set(x, "onValue", value.asInstanceOf[js.Any])
    
    inline def setOnValueUndefined: Self = StObject.set(x, "onValue", js.undefined)
    
    inline def setSize(value: Double): Self = StObject.set(x, "size", value.asInstanceOf[js.Any])
    
    inline def setSizeUndefined: Self = StObject.set(x, "size", js.undefined)
  }
}
