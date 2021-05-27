package viewer

import models.JSONNumber
import models.JSONString
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import visitor.JSONValue
import visitor.JSONVisitor

fun main() {
    JSONTreeSkeleton().open()
}

data class Dummy(val number: Int)

class JSONTreeSkeleton() {
    val shell: Shell
    val tree: Tree

    init {
        val display = Display()
        shell = Shell(display)

        shell.text = "File tree skeleton"
        shell.layout = GridLayout(2,true)

        tree = Tree(shell, SWT.BORDER)
        tree.layoutData = GridData(GridData.FILL_BOTH)

        val info = Label(shell, SWT.BORDER or SWT.WRAP)
        info.layoutData = GridData(GridData.FILL_BOTH)

        val a = TreeItem(tree, SWT.NONE)
        a.text = "A"
        a.data = JSONString("A First")

        val b = TreeItem(tree, SWT.NONE)
        b.text = "B"
        b.data = JSONNumber(2)

        val c = TreeItem(b, SWT.NONE)
        a.background = Color(RGB(0,255,0))
        c.text = "CCCCCCCCCCCCCCCCCC"
        c.data = JSONNumber(3)


        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                tree.selection.first().background = Color(RGB(0,255,0))
                val item = tree.selection.first().data as JSONValue
                var jsonObjectSerializer = JSONVisitor()
                item.accept(jsonObjectSerializer)
                info.text = jsonObjectSerializer.jsonString
                println("selected: " + tree.selection.first().data)
            }
        })

        val text = Text(shell,  SWT.NONE)
        text.layoutData = GridData(GridData.FILL_HORIZONTAL)
        text.addModifyListener { print(text.text); }

        val button = Button(shell, SWT.PUSH)
        button.text = "depth"
        button.addSelectionListener(object: SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val item = tree.selection.first()
                info.text = item.depth().toString()
            }
        })



    }

    // auxiliar para profundidade do nó
    fun TreeItem.depth(): Int =
        if(parentItem == null) 0
        else 1 + parentItem.depth()


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
