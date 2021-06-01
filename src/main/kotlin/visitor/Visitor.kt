package visitor

import models.JSONObject

interface Visitor {
    fun visit(l: JSONLeaf)
    fun visit(c: JSONComposite): Boolean
    fun visit(jsonKeyValuePair: JSONObject.JSONKeyValuePair) { }
    fun endVisit(c: JSONValue) { }
    fun endVisit(l: JSONComposite) { }
}