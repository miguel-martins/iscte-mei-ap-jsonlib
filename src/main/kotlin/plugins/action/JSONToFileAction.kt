package plugins.action

import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import viewer.JSONViewer
import java.io.File

class JSONToFileAction: ActionPlugin {
    override val name: String
        get() = "Write JSON to file"

    override fun execute(viewer: JSONViewer) {
        val shell = Shell(viewer.shell)

        shell.text = name
        shell.layout = GridLayout(1,true)

        val label = Label(shell, SWT.BORDER or SWT.WRAP)
        label.text = "File path"

        val filePath = Text(shell,  SWT.NONE)

        val button = Button(shell, SWT.PUSH)
        button.layoutData = GridData(GridData.FILL_HORIZONTAL)
        button.text = "Write JSON"

        button.addSelectionListener(object: SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val path = filePath.text
                if(path.isNotEmpty()){
                    File(path).writeText(viewer.jsonDisplay.text)
                    shell.close()
                }
            }
        })

        shell.pack()
        shell.open()

    }

}