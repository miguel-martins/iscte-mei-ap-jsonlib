package plugins.action

import models.JSONObject
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import viewer.JSONViewer
import visitor.JSONComposite

class JSONCompositeAddAction: ActionPlugin {
    override val name: String
        get() = "Add property"

    override fun execute(viewer: JSONViewer) {
        val item: TreeItem = viewer.tree.selection.first()
        val value = item.data

        if(value is JSONComposite) {
            val shell = Shell(viewer.shell)
            shell.text = name
            shell.layout = GridLayout(1,true)

            if (value is JSONObject) {
                val keyLabel = Label(shell, SWT.BORDER)
                keyLabel.text = "Key"
                val newKey = Text(shell,  SWT.NONE)
            }



            val nullButton = Button(shell, SWT.PUSH)
            nullButton.layoutData = GridData(GridData.FILL_HORIZONTAL)
            nullButton.text = "Null Value"

            val stringButton = Button(shell, SWT.PUSH)
            stringButton.layoutData = GridData(GridData.FILL_HORIZONTAL)
            stringButton.text = "String Value"

            val numberButton = Button(shell, SWT.PUSH)
            numberButton.layoutData = GridData(GridData.FILL_HORIZONTAL)
            numberButton.text = "Number Value"

            val booleanButton = Button(shell, SWT.PUSH)
            booleanButton.layoutData = GridData(GridData.FILL_HORIZONTAL)
            booleanButton.text = "Number Value"
/*
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
            })*/

            shell.pack()
            shell.open()
        }


    }
}