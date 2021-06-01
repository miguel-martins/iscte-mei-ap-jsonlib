package viewer

import auxiliary.expandAll
import auxiliary.traverse
import generator.*
import injector.Injector
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import plugins.action.ActionPlugin
import plugins.presentation.PresentationPlugin
import visitor.JSONTreeVisitor
import visitor.JSONValue
import visitor.JSONVisitor

fun main() {
    Injector.create(JSONViewer(JSONGenerator().buildFrom(mutableListOf(1,false,"hello",json)))).open()
    //JSONViewer(JSONGenerator().buildFrom(mutableListOf(1,false,"hello",json))).open()
}

data class JSONClass(val list: MutableList<Any>,@JSONExclude val nullValue: Nothing?, @JSONIdentifier("stringId") val stringValue: String, val numberValue: Number, val booleanValue: Boolean, val type: CustomType, val child: JSONClass? = null)
enum class CustomType {
    CustomType1, CustomType2, CustomType3
}

val json = JSONClass(mutableListOf(1,false,"hello"),null,"json1", 1, false, CustomType.CustomType1)
val json2 = JSONClass(mutableListOf(1,false,"hello"),null,"json2", 2, true, CustomType.CustomType2, json)

class JSONViewer(val value : JSONValue) {

    val shell: Shell
    var tree: Tree
    val jsonDisplay: Label
    val searchText: Text

    //Plugins
    @Inject
    private lateinit var presentation: PresentationPlugin

    @InjectAdd
    private var actions = mutableListOf<ActionPlugin>()

    init {
        val display = Display()
        shell = Shell(display)

        shell.text = "Jasus"
        shell.layout = GridLayout(2,true)

        tree = Tree(shell, SWT.BORDER)
        createTree(tree, value)

        tree.layoutData = GridData(GridData.FILL_BOTH)

        jsonDisplay = Label(shell, SWT.BORDER or SWT.WRAP)
        jsonDisplay.layoutData = GridData(GridData.FILL_BOTH)

        searchText = Text(shell,  SWT.NONE)
        searchText.layoutData = GridData(GridData.FILL_HORIZONTAL)

        addViewerListeners(searchText, tree)
    }

    private fun addViewerListeners(searchText: Text, tree: Tree) {
        searchText.addModifyListener { highlightElementsWith(searchText.text); }

        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                displaySerializedJSON(tree.selection.first().data as JSONValue)
            }
        })
    }

    private fun createTree(tree: Tree, value: JSONValue): Tree {
        val visitor = JSONTreeVisitor(tree)
        value.accept(visitor)
        return visitor.tree
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
        applyPlugins()
        shell.pack()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }

    private fun applyPlugins() {
        fun applyPresentationPlugin() {
            if(this::presentation.isInitialized)
                presentation.execute(this)
        }

        fun applyActionPlugins() {
            val viewer = this
            actions.forEach {
                val button = Button(shell, SWT.PUSH)
                button.text = it.name
                button.addSelectionListener(object: SelectionAdapter() {
                    override fun widgetSelected(e: SelectionEvent) {
                        it.execute(viewer)
                    }
                })
            }
        }
        applyPresentationPlugin()
        applyActionPlugins()
    }

    fun repaint() {
        tree.removeAll()
        createTree(tree, value)
        tree.expandAll()
    }


}


