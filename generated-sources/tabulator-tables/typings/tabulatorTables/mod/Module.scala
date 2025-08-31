package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

// tslint:disable-next-line:no-unnecessary-class
@JSImport("tabulator-tables", "Module")
@js.native
open class Module protected () extends StObject {
  /**
    * The constructor is called as the module is being instantiated and is where your module should start to tell tabulator a little about itself.
    * The constructor takes one argument, the table the module is being bound to, it should pass this to the super function so that it is available for the module to bind to its internal helper functions.
    * It is very important that you do not try any access any parts of the table, any events or other modules when the constructor is called.
    * At this point the table is in the process of being built and is not ready to respond to anything.
    * The constructor should be used to register any external functionality that may be called on the module and to register andy setup options that may be set on the table or column definitions.
    *
    * @param table The Tabulator object the module is being initialized for
    */
  def this(table: Tabulator) = this()
  
  /**
    * Fire an forget an event that can be consumed by external consumers
    * @param eventName Event name, must follow the `camelCase` convention
    * @param args Arguments for the event
    */
  def dispatchExternal(eventName: String, args: Any*): Unit = js.native
  
  /**
    * Called by the table when it is ready for module integrations
    */
  def initialize(): Unit = js.native
  
  /**
    * Register an option for the column component
    * @param propName Property name to add
    * @param defaultValue Default value of the property
    */
  def registerColumnOption(propName: String): Unit = js.native
  def registerColumnOption(propName: String, defaultValue: Any): Unit = js.native
  
  /**
    * Make a function available on the table object
    * @param functionName Function to add
    * @param callback Function to be called when the method is invoked on the grid
    */
  def registerTableFunction(functionName: String, callback: js.Function1[/* repeated */ Any, Any]): Unit = js.native
  
  /**
    * Adds an option to the table constructor
    * @param propName Property name to add
    * @param defaultValue Default value of the property
    */
  def registerTableOption(propName: String): Unit = js.native
  def registerTableOption(propName: String, defaultValue: Any): Unit = js.native
  
  def reloadData(data: String, silent: Boolean, columnsChanged: Boolean): js.Promise[Unit] = js.native
  /**
    * Uses the data loader to reload the data in the grid
    * @param data New grid data
    * @param silent Do not trigger any events
    * @param columnsChanged If the column configuration has changed
    * @returns a promise that resolves when the data update is competed
    */
  def reloadData(data: js.Array[Any], silent: Boolean, columnsChanged: Boolean): js.Promise[Unit] = js.native
  
  /**
    * Updates the configuration of the grid.
    * It should be noted that changing an option will not automatically update the table to reflect that change,
    * you will likely need to call the refreshData function to trigger the update.
    * @param key Key to update
    * @param value value to set
    */
  def setOption(
    key: /* keyof tabulator-tables.tabulator-tables.Options */ /* import warning: LimitUnionLength.leaveTypeRef Was union type with length 197, starting with typings.tabulatorTables.tabulatorTablesStrings.height, typings.tabulatorTables.tabulatorTablesStrings.maxHeight, typings.tabulatorTables.tabulatorTablesStrings.minHeight */ Any,
    value: Any
  ): Unit = js.native
  
  /**
    * Subscribe to an event in the Tabulator Event bus.
    * See https://tabulator.info/docs/5.5/events-internal
    * @param eventName Event to subscribe to
    * @param callback Function to call when subscribing
    * @param order The order for initialization. By default, it's 10000. See https://tabulator.info/docs/5.5/module-build#events-internal
    */
  def subscribe(eventName: String, callback: js.Function1[/* repeated */ Any, Any]): Unit = js.native
  def subscribe(eventName: String, callback: js.Function1[/* repeated */ Any, Any], order: Double): Unit = js.native
  
  /**
    * Reference to the table this module is in
    */
  var table: Tabulator = js.native
  
  /**
    * Unsubscribe to an event in the Tabulator Event bus.
    * See https://tabulator.info/docs/5.5/events-internal
    * @param eventName Event to subscribe to
    * @param callback Function to call when subscribing
    */
  def unsubscribe(eventName: String, callback: js.Function1[/* repeated */ Any, Any]): Unit = js.native
}
object Module {
  
  @JSImport("tabulator-tables", "Module")
  @js.native
  val ^ : js.Any = js.native
  
  /**
    * The optional static `moduleInitOrder` property can be used to determine the order in which the module is initialized,
    * by default modules are initialized with a value of 0.
    * If you want your module to be initialized before other modules use a minus number, if you want it initialized after use a positive number.
    */
  /* static member */
  @JSImport("tabulator-tables", "Module.moduleInitOrder")
  @js.native
  def moduleInitOrder: js.UndefOr[Double] = js.native
  inline def moduleInitOrder_=(x: js.UndefOr[Double]): Unit = ^.asInstanceOf[js.Dynamic].updateDynamic("moduleInitOrder")(x.asInstanceOf[js.Any])
  
  /**
    * The static `moduleName` property must be declared on the class (not an instance of the class),
    * and be a camelCase name for the module, this is used internally by the table to act as a unique identifier for the module.
    */
  /* static member */
  @JSImport("tabulator-tables", "Module.moduleName")
  @js.native
  def moduleName: String = js.native
  inline def moduleName_=(x: String): Unit = ^.asInstanceOf[js.Dynamic].updateDynamic("moduleName")(x.asInstanceOf[js.Any])
}
