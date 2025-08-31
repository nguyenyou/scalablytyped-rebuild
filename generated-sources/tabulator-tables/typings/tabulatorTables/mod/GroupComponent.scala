package typings.tabulatorTables.mod

import org.scalajs.dom.HTMLElement
import typings.tabulatorTables.tabulatorTablesBooleans.`false`
import typings.tabulatorTables.tabulatorTablesStrings.bottom
import typings.tabulatorTables.tabulatorTablesStrings.center
import typings.tabulatorTables.tabulatorTablesStrings.nearest
import typings.tabulatorTables.tabulatorTablesStrings.top
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

@js.native
trait GroupComponent extends StObject {
  
  /** The getElement function returns the DOM node for the group header. */
  def getElement(): HTMLElement = js.native
  
  /** Returns the string of the field that all rows in this group have been grouped by. (if a function is used to group the rows rather than a field, this function will return false) */
  def getField(): String = js.native
  
  /** The getKey function returns the unique key that is shared between all rows in this group. */
  def getKey(): Any = js.native
  
  /** The getParentGroup function returns the GroupComponent for the parent group of this group. if no parent exists, this function will return false. */
  def getParentGroup(): GroupComponent | `false` = js.native
  
  /** The getRows function returns an array of RowComponent objects, one for each row in the group */
  def getRows(): js.Array[RowComponent] = js.native
  
  /** The getSubGroups function returns an array of GroupComponent objects, one for each sub group of this group. */
  def getSubGroups(): js.Array[GroupComponent] = js.native
  
  /** The getTable function returns the Tabulator object for the table containing the group */
  def getTable(): Tabulator = js.native
  
  /** The hide function hides the group if it is visible. */
  def hide(): Unit = js.native
  
  /** The isVisible function returns a boolean to show if the group is visible, a value of true means it is visible. */
  def isVisible(): Boolean = js.native
  
  def popup(contents: String, position: PopupPosition): Unit = js.native
  
  /** The scrollTo function will scroll the table to the group header if it passes the current filters. */
  def scrollTo(): js.Promise[Unit] = js.native
  def scrollTo(position: top | center | bottom | nearest): js.Promise[Unit] = js.native
  def scrollTo(position: top | center | bottom | nearest, scrollIfVisible: Boolean): js.Promise[Unit] = js.native
  def scrollTo(position: Unit, scrollIfVisible: Boolean): js.Promise[Unit] = js.native
  
  /** The show function shows the group if it is hidden. */
  def show(): Unit = js.native
  
  /** The toggle function toggles the visibility of the group, switching between hidden and visible. */
  def toggle(): Unit = js.native
}
