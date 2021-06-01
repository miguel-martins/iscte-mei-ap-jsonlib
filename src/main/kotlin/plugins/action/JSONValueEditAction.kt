package plugins.action

import auxiliary.expandAll
import models.JSONBoolean
import models.JSONNull
import models.JSONNumber
import models.JSONString
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import viewer.JSONViewer
import visitor.JSONLeaf
import java.io.File

class JSONValueEditAction: ActionPlugin {
    override val name: String
        get() = "Edit Value"

    override fun execute(viewer: JSONViewer) {
        val item: TreeItem = viewer.tree.selection.first()
        val value = item.data
        if(value is JSONLeaf && value !is JSONNull) {
            val shell = Shell(viewer.shell)

            shell.text = name
            shell.layout = GridLayout(1,true)

            val label = Label(shell, SWT.BORDER or SWT.WRAP)
            label.text = "New value"

            val newValue = Text(shell,  SWT.NONE)

            val button = Button(shell, SWT.PUSH)
            button.layoutData = GridData(GridData.FILL_HORIZONTAL)
            button.text = "Edit JSON"

            button.addSelectionListener(object: SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    when(value) {
                        is JSONBoolean -> {
                            value.value = newValue.text.toBoolean()
                        }
                        is JSONNumber -> {
                            value.value = newValue.text.toInt()
                        }
                        else ->  (value as JSONString).value = newValue.text
                    }
                    viewer.repaint()
                    shell.close()
                }
            })

            shell.pack()
            shell.open()
        }


    }
}