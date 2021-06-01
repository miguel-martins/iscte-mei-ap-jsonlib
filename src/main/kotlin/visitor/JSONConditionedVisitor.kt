package visitor

import models.JSONObject

class JSONConditionedVisitor(val filter: (JSONValue) -> Boolean): Visitor{
    var values: MutableList<JSONValue> = mutableListOf()

    override fun visit(l: JSONLeaf) {
        if(filter(l))
            values.add(l)
    }

    override fun visit(c: JSONComposite): Boolean {
        if(filter(c))
            values.add(c)
        return true
    }

    override fun visit(jsonKeyValuePair: JSONObject.JSONKeyValuePair) {
        if(filter(jsonKeyValuePair))
            values.add(jsonKeyValuePair)
    }
}