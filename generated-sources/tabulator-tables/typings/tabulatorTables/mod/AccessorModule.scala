package typings.tabulatorTables.mod

import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

@JSImport("tabulator-tables", "AccessorModule")
@js.native
open class AccessorModule protected () extends Module {
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
}
