package plugins.presentation

import auxiliary.traverse
import models.JSONArray
import models.JSONObject
import models.JSONString
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.ImageData
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.TreeItem
import viewer.JSONViewer
import visitor.JSONComposite
import visitor.JSONLeaf
import visitor.JSONValue
import visitor.JSONVisitor

class ExamplePresentation: PresentationPlugin {
    private val FOLDER_ICON_LOCATION: String = "./src/main/kotlin/media/folder.png"
    private val ICON_ICON_LOCATION: String = "./src/main/kotlin/media/file.png"

    override fun execute(viewer: JSONViewer) {
    val toDispose = mutableListOf<TreeItem>()
      viewer.tree.traverse {
          if(it.data is JSONString)
              toDispose.add(it)
          else {
              it.image = getIconFor(it.data as JSONValue)
              it.text = getTextFor(it)
          }
      }
    toDispose.forEach { it.dispose() }
    }

    private fun getTextFor(item: TreeItem): String {
        return item.text.split(":")[0].removePrefix("\"").removeSuffix("\"")
    }

    private fun getIconFor(value: JSONValue): Image{
        val iconSource = if(value is JSONComposite) FOLDER_ICON_LOCATION else ICON_ICON_LOCATION
        return Image(Display.getDefault(), ImageData(iconSource).scaledTo(20,20))
    }

}