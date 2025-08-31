package typings.tabulatorTables.mod

import org.scalajs.dom.Blob
import typings.tabulatorTables.tabulatorTablesBooleans.`false`
import org.scalablytyped.runtime.StObject
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

trait OptionsDownload extends StObject {
  
  /**
    * By default Tabulator includes column headers, row groups and column calculations in the download output.
    *
    * You can choose to remove column headers groups, row groups or column calculations from the output data by setting the values in the downloadConfig option in the table definition:
    */
  var downloadConfig: js.UndefOr[AdditionalExportOptions] = js.undefined
  
  /** If you want to make any changes to the table data before it is parsed into the download file you can pass a mutator function to the downloadDataFormatter callback. */
  var downloadDataFormatter: js.UndefOr[js.Function1[/* data */ Any, Any]] = js.undefined
  
  /**
    * The downloadEncoder callback allows you to intercept the download file data before the users is prompted to save the file.
    *
    * The first argument of the function is the file contents returned from the downloader, the second argument is the suggested mime type for the output. The function is should return a blob of the file to be downloaded.
    */
  var downloadEncoder: js.UndefOr[js.Function2[/* fileContents */ Any, /* mimeType */ String, Blob | `false`]] = js.undefined
  
  /**
    * The downloadReady callback allows you to intercept the download file data before the users is prompted to save the file.
    *
    * In order for the download to proceed the downloadReady callback is expected to return a blob of file to be downloaded.
    *
    * If you would prefer to abort the download you can return false from this callback. This could be useful for example if you want to send the created file to a server via ajax rather than allowing the user to download the file.
    */
  var downloadReady: js.UndefOr[js.Function2[/* fileContents */ Any, /* blob */ Blob, Blob | `false`]] = js.undefined
  
  /** By default, only the active rows (rows that have passed filtering) will be included in the download the downloadRowRange option takes a Row Range Lookup value and allows you to choose which rows are included in the download output. */
  var downloadRowRange: js.UndefOr[RowRangeLookup] = js.undefined
}
object OptionsDownload {
  
  inline def apply(): OptionsDownload = {
    val __obj = js.Dynamic.literal()
    __obj.asInstanceOf[OptionsDownload]
  }
  
  @scala.inline
  implicit open class MutableBuilder[Self <: OptionsDownload] (val x: Self) extends AnyVal {
    
    inline def setDownloadConfig(value: AdditionalExportOptions): Self = StObject.set(x, "downloadConfig", value.asInstanceOf[js.Any])
    
    inline def setDownloadConfigUndefined: Self = StObject.set(x, "downloadConfig", js.undefined)
    
    inline def setDownloadDataFormatter(value: /* data */ Any => Any): Self = StObject.set(x, "downloadDataFormatter", js.Any.fromFunction1(value))
    
    inline def setDownloadDataFormatterUndefined: Self = StObject.set(x, "downloadDataFormatter", js.undefined)
    
    inline def setDownloadEncoder(value: (/* fileContents */ Any, /* mimeType */ String) => Blob | `false`): Self = StObject.set(x, "downloadEncoder", js.Any.fromFunction2(value))
    
    inline def setDownloadEncoderUndefined: Self = StObject.set(x, "downloadEncoder", js.undefined)
    
    inline def setDownloadReady(value: (/* fileContents */ Any, /* blob */ Blob) => Blob | `false`): Self = StObject.set(x, "downloadReady", js.Any.fromFunction2(value))
    
    inline def setDownloadReadyUndefined: Self = StObject.set(x, "downloadReady", js.undefined)
    
    inline def setDownloadRowRange(value: RowRangeLookup): Self = StObject.set(x, "downloadRowRange", value.asInstanceOf[js.Any])
    
    inline def setDownloadRowRangeUndefined: Self = StObject.set(x, "downloadRowRange", js.undefined)
  }
}
