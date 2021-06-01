package plugins.action

import models.*
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import viewer.JSONViewer
import visitor.JSONComposite
import visitor.JSONLeaf

enum class ButtonType {
    STRING, BOOLEAN, NUMBER
}


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
            booleanButton.text = "Boolean Value"

            nullButton.addSelectionListener(object: SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    val newValue = JSONNull()
                    addNewProperty(newValue, value, shell, viewer)
                }
            })

            stringButton.addSelectionListener(object: SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    createProperty(value, shell, viewer, ButtonType.STRING)
                }
            })

            numberButton.addSelectionListener(object: SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    createProperty(value, shell, viewer, ButtonType.NUMBER)
                }
            })

            booleanButton.addSelectionListener(object: SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    createProperty(value, shell, viewer, ButtonType.BOOLEAN)
                }
            })

            shell.pack()
            shell.open()
        }
    }

    private fun createProperty(parent: JSONComposite, shell: Shell, viewer: JSONViewer,buttonType: ButtonType) {
        val newValueShell = Shell(shell)
        newValueShell.text = "Enter the property value"
        newValueShell.layout = GridLayout(2,true)

        val valueLabel = Label(newValueShell, SWT.BORDER)
        valueLabel.text = "Value: "

        val newValue = Text(newValueShell,  SWT.NONE)

        val button = Button(newValueShell, SWT.PUSH)
        button.layoutData = GridData(GridData.FILL_HORIZONTAL)
        button.text = "Create property"
        button.addSelectionListener(object: SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val text = newValue.text
                if (text.isNotEmpty()) {
                    val newValue = when(buttonType) {
                        ButtonType.STRING -> JSONString(text)
                        ButtonType.NUMBER -> if (text.toIntOrNull() != null) JSONNumber(text.toInt()) else null
                        else -> if(text.toLowerCase() == "true" || text.toLowerCase() == "false")  JSONBoolean(text.toLowerCase().toBoolean()) else null
                    }
                    if (newValue != null)
                        addNewProperty(newValue, parent, newValueShell, viewer)
                }
            }
        })

        newValueShell.pack()
        newValueShell.open()
    }

    private fun addNewProperty(newValue: JSONLeaf, parent: JSONComposite, shell: Shell, viewer: JSONViewer) {
        if (parent is JSONArray) {
            parent.values.add(newValue)
            viewer.repaint()
        }
        else {
            val newKeyShell = Shell(shell)
            newKeyShell.text = "Enter the property key"
            newKeyShell.layout = GridLayout(2,true)

            val keyLabel = Label(newKeyShell, SWT.BORDER)
            keyLabel.text = "Key: "

            val newKey = Text(newKeyShell,  SWT.NONE)

            val button = Button(newKeyShell, SWT.PUSH)
            button.layoutData = GridData(GridData.FILL_HORIZONTAL)
            button.text = "Create property"
            button.addSelectionListener(object: SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent) {
                    if (newKey.text.isNotEmpty()) {
                        val newKeyValuePair = JSONObject.JSONKeyValuePair(newKey.text, newValue)
                        (parent as JSONObject).values.add(newKeyValuePair)
                        newKeyShell.close()
                        viewer.repaint()
                    }
                }
            })
            newKeyShell.pack()
            newKeyShell.open()
        }

    }
}