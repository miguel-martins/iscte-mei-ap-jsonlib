package visitor

import models.JSONObject
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Tree
import org.eclipse.swt.widgets.TreeItem
import java.util.*

class JSONTreeVisitor(var tree: Tree) : Visitor{


    private var parents: Stack<Pair<TreeItem, Boolean>> = Stack()


    /*
    *   Creates a new TreeItem with the correct parent
    * */
    private fun createTreeItem(element: JSONValue): TreeItem? {
        if(parents.isEmpty())
            return TreeItem(tree, SWT.NONE)

        val parent = parents.peek()

        if(parent.second) {
            parent.first.data = element
            parents.pop()
            if(element is JSONLeaf)
                return null
        }

        return TreeItem(parent.first, SWT.NONE)

    }

    /*
    *   Reuses the JSON Serializer to get the necessary strings
    * */
    private fun getSerializedString(value: JSONValue):String {
        val jsonObjectSerializer = JSONVisitor()
        value.accept(jsonObjectSerializer)
        return jsonObjectSerializer.jsonString
    }

    override fun visit(l: JSONLeaf) {
        val newTreeItem = createTreeItem(l)
        if(newTreeItem != null) {
            newTreeItem.text = getSerializedString(l)
            newTreeItem.data = l
        }
    }

    override fun visit(c: JSONComposite): Boolean {

        val treeItemText = when(c) {
            is JSONObject -> "{ object }"
            else  -> "{ array }"
        }
        var newTreeItem = createTreeItem(c)

        newTreeItem!!.text = treeItemText
        newTreeItem.data = c

        parents.push(Pair(newTreeItem, false))
        return true
    }

    override fun endVisit(l: JSONComposite) {
        parents.pop()
    }

    override fun visit(keyValPair: JSONObject.JSONKeyValuePair) {
        val treeItemText = if(keyValPair.value is JSONLeaf) getSerializedString(keyValPair) else keyValPair.key
        val newTreeItem = createTreeItem(keyValPair)
        newTreeItem!!.text = treeItemText
        newTreeItem.data = keyValPair.value
        parents.push(Pair(newTreeItem, true))
    }

}