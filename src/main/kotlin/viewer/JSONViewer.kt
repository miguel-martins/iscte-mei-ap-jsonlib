package viewer

import auxiliary.expandAll
import auxiliary.traverse
import generator.JSONExclude
import generator.JSONGenerator
import generator.JSONIdentifier
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import visitor.JSONTreeVisitor
import visitor.JSONValue
import visitor.JSONVisitor

fun main() {
    JSONViewer(JSONGenerator().buildFrom(mutableListOf(1,false,"hello",json))).open()
}

data class JSONClass(val list: MutableList<Any>,@JSONExclude val nullValue: Nothing?, @JSONIdentifier("stringId") val stringValue: String, val numberValue: Number, val booleanValue: Boolean, val type: CustomType, val child: JSONClass? = null)
enum class CustomType {
    CustomType1, CustomType2, CustomType3
}

val json = JSONClass(mutableListOf(1,false,"hello"),null,"json1", 1, false, CustomType.CustomType1)
val json2 = JSONClass(mutableListOf(1,false,"hello"),null,"json2", 2, true, CustomType.CustomType2, json)

class JSONViewer(value : JSONValue) {

    private val shell: Shell
    private val tree: Tree
    private val jsonDisplay: Label
    private val searchText: Text

    init {
        val display = Display()
        shell = Shell(display)

        shell.text = "Jasabiaqueiacorrermal"
        shell.layout = GridLayout(2,true)

        tree = createTree(value, shell)
        tree.layoutData = GridData(GridData.FILL_BOTH)

        jsonDisplay = Label(shell, SWT.BORDER or SWT.WRAP)
        jsonDisplay.layoutData = GridData(GridData.FILL_BOTH)

        searchText = Text(shell,  SWT.NONE)
        searchText.layoutData = GridData(GridData.FILL_HORIZONTAL)

        addViewerListeners()

    }

    private fun addViewerListeners() {
        searchText.addModifyListener { highlightElementsWith(searchText.text); }

        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                displaySerializedJSON(tree.selection.first().data as JSONValue)
            }
        })
    }

    private fun createTree(value: JSONValue, shell: Shell): Tree {
        val visitor = JSONTreeVisitor(shell)
        value.accept(visitor)
        return visitor.getTree()
    }

    private fun displaySerializedJSON(item: JSONValue) {
        var jsonObjectSerializer = JSONVisitor()
        item.accept(jsonObjectSerializer)
        jsonDisplay.text = jsonObjectSerializer.jsonString
    }

    private fun highlightElementsWith(text: String) {
        tree.traverse {
            it.background = null
            if (text.isNotEmpty() && it.text.toLowerCase().contains(text))
                    it.background =  Color(46, 139, 87)
        }
    }

    fun open() {
        tree.expandAll()
        shell.pack()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }


}


