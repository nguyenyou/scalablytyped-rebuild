package typings.tabulatorTables.mod

import typings.tabulatorTables.anon.End
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait RangeComponent extends StObject {
  
  /**
    * You can clear the value of every cell in a range by calling the clearValues function on the range:
    *
    * ```javascript
    * var data = range.clearValues();
    * ```
    * This will set the value of every cell in the range to the value of the selectableRangeClearCellsValue table
    * option, which is set to undefined by default.
    */
  def clearValues(): Unit
  
  /**
    * You can find the position number for the bottom row of the range by calling the getBottomEdge function on the range:
    *
    * @example
    * var bottomPosition = range.getBottomEdge();
    */
  def getBottomEdge(): Double
  
  /**
    * You can retrieve the bounds of a range by calling the getBounds function on the range:
    *
    * ```javascript
    * var bounds = range.getBounds();
    * ```
    * This will return an object containing two Cell Components, for the two bounds of the range
    *
    * ```json
    * {
    *     start:Component, //the cell component at the top left of the range
    *     end:Component, //the cell component at the bottom right of the range
    * }
    * ```
    */
  def getBounds(): End
  
  /**
    * You can retrieve all the Cell Components in a range by calling the getCells function on the range:
    *
    * ```javascript
    * var cells = range.getCells();
    * ```
    * This will return a array of Cell Components
    */
  def getCells(): js.Array[CellComponent]
  
  /**
    * You can retrieve all the Column Components in a range by calling the getColumns function on the range:
    *
    * ```javascript
    * var columns = range.getColumns();
    * ```
    * This will return a array of Column Components
    */
  def getColumns(): js.Array[ColumnComponent]
  
  /**
    * You can retrieve the cell data for a range by calling the getData function on the range:
    *
    * ```javascript
    * var data = range.getData();
    * ```
    *
    * This will return a range data array, which is structured as a series of row data objects with only the props for
    * cells in that range:
    *
    * ```json
    * [
    *     {color:"green", country:"England", driver:true}, //data for selected cells in first row in range
    *     {color:"red", country:"USA", driver:false}, //data for selected cells in second row in range
    *     {color:"blue", country:"France", driver:true}, //data for selected cells in third row in range
    * ]
    * ```
    */
  def getData(): Any
  
  /**
    * You can retrieve the bounding rectangle element for a range by calling the getElement function on the range:
    *
    * @example
    * var element = range.getElement();
    */
  def getElement(): Any
  
  /**
    * You can find the position number for the left column of the range by calling the getLeftEdge function on the range:
    *
    * @example
    * var leftPosition = range.getLeftEdge();
    */
  def getLeftEdge(): Double
  
  /**
    * You can find the position number for the right column of the range by calling the getRightEdge function on the range:
    *
    * @example
    * var rightPosition = range.getRightEdge();
    */
  def getRightEdge(): Double
  
  /**
    * You can retrieve all the Row Components in a range by calling the getRows function on the range:
    *
    * ```javascript
    * var rows = range.getRows();
    * ```
    * This will return a array of Row Components
    */
  def getRows(): js.Array[RowComponent]
  
  /**
    * You can retrieve a structured map of all the Cell Components in a range by calling the getStructuredCells
    * function on the range:
    *
    * ```javascript
    * var cells = range.getStructuredCells();
    * ```
    * This will return a array of row arrays, with each row array containing the Cell Components in order for that row:
    *
    * ```json
    * [
    *     [Component, Component, Component], //first row
    *     [Component, Component, Component], //second row
    *     [Component, Component, Component], //third row
    * ]
    * ```
    */
  def getStructuredCells(): js.Array[js.Array[CellComponent]]
  
  /**
    * You can find the position number for the top row of the range by calling the getTopEdge function on the range:
    *
    * @example
    * var topPosition = range.getTopEdge();
    */
  def getTopEdge(): Double
  
  /**
    * You can remove a range by calling the remove function on the range:
    *
    * @example
    * range.remove();
    */
  def remove(): Unit
  
  /**
    * You can update the bounds for an existing range using the setBounds function, passing in the Cell Components
    * for the top-left and bottom-right bounds of the selection:
    *
    * @example
    * var topLeft = table.getRows()[2].getCells()[1];
    * var bottomRight = table.getRows()[5].getCells()[6];
    *
    * range.setBounds(topLeft, bottomRight);
    */
  def setBounds(topLeft: CellComponent, bottomRight: CellComponent): Unit
  
  /**
    * You can change the bottom right ending edge of an existing range using the setEndBound function, passing in the
    * Cell Component for the bottom right bound of the selection:
    *
    * @example
    * var bottomRight = table.getRows()[5].getCells()[6];
    *
    * range.setEndBound(bottomRight);
    */
  def setEndBound(cell: CellComponent): Unit
  
  /**
    * You can change the top left start edge of an existing range using the setStartBound function, passing in the
    * Cell Component for the top left bound of the selection:
    *
    * @example
    * var topLeft = table.getRows()[2].getCells()[1];
    *
    * range.setStartBound(topLeft);
    */
  def setStartBound(cell: CellComponent): Unit
}
object RangeComponent {
  
  inline def apply(
    clearValues: () => Unit,
    getBottomEdge: () => Double,
    getBounds: () => End,
    getCells: () => js.Array[CellComponent],
    getColumns: () => js.Array[ColumnComponent],
    getData: () => Any,
    getElement: () => Any,
    getLeftEdge: () => Double,
    getRightEdge: () => Double,
    getRows: () => js.Array[RowComponent],
    getStructuredCells: () => js.Array[js.Array[CellComponent]],
    getTopEdge: () => Double,
    remove: () => Unit,
    setBounds: (CellComponent, CellComponent) => Unit,
    setEndBound: CellComponent => Unit,
    setStartBound: CellComponent => Unit
  ): RangeComponent = {
    val __obj = js.Dynamic.literal(clearValues = js.Any.fromFunction0(clearValues), getBottomEdge = js.Any.fromFunction0(getBottomEdge), getBounds = js.Any.fromFunction0(getBounds), getCells = js.Any.fromFunction0(getCells), getColumns = js.Any.fromFunction0(getColumns), getData = js.Any.fromFunction0(getData), getElement = js.Any.fromFunction0(getElement), getLeftEdge = js.Any.fromFunction0(getLeftEdge), getRightEdge = js.Any.fromFunction0(getRightEdge), getRows = js.Any.fromFunction0(getRows), getStructuredCells = js.Any.fromFunction0(getStructuredCells), getTopEdge = js.Any.fromFunction0(getTopEdge), remove = js.Any.fromFunction0(remove), setBounds = js.Any.fromFunction2(setBounds), setEndBound = js.Any.fromFunction1(setEndBound), setStartBound = js.Any.fromFunction1(setStartBound))
    __obj.asInstanceOf[RangeComponent]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: RangeComponent] (val x: Self) extends AnyVal {
    
    inline def setClearValues(value: () => Unit): Self = StObject.set(x, "clearValues", js.Any.fromFunction0(value))
    
    inline def setGetBottomEdge(value: () => Double): Self = StObject.set(x, "getBottomEdge", js.Any.fromFunction0(value))
    
    inline def setGetBounds(value: () => End): Self = StObject.set(x, "getBounds", js.Any.fromFunction0(value))
    
    inline def setGetCells(value: () => js.Array[CellComponent]): Self = StObject.set(x, "getCells", js.Any.fromFunction0(value))
    
    inline def setGetColumns(value: () => js.Array[ColumnComponent]): Self = StObject.set(x, "getColumns", js.Any.fromFunction0(value))
    
    inline def setGetData(value: () => Any): Self = StObject.set(x, "getData", js.Any.fromFunction0(value))
    
    inline def setGetElement(value: () => Any): Self = StObject.set(x, "getElement", js.Any.fromFunction0(value))
    
    inline def setGetLeftEdge(value: () => Double): Self = StObject.set(x, "getLeftEdge", js.Any.fromFunction0(value))
    
    inline def setGetRightEdge(value: () => Double): Self = StObject.set(x, "getRightEdge", js.Any.fromFunction0(value))
    
    inline def setGetRows(value: () => js.Array[RowComponent]): Self = StObject.set(x, "getRows", js.Any.fromFunction0(value))
    
    inline def setGetStructuredCells(value: () => js.Array[js.Array[CellComponent]]): Self = StObject.set(x, "getStructuredCells", js.Any.fromFunction0(value))
    
    inline def setGetTopEdge(value: () => Double): Self = StObject.set(x, "getTopEdge", js.Any.fromFunction0(value))
    
    inline def setRemove(value: () => Unit): Self = StObject.set(x, "remove", js.Any.fromFunction0(value))
    
    inline def setSetBounds(value: (CellComponent, CellComponent) => Unit): Self = StObject.set(x, "setBounds", js.Any.fromFunction2(value))
    
    inline def setSetEndBound(value: CellComponent => Unit): Self = StObject.set(x, "setEndBound", js.Any.fromFunction1(value))
    
    inline def setSetStartBound(value: CellComponent => Unit): Self = StObject.set(x, "setStartBound", js.Any.fromFunction1(value))
  }
}
