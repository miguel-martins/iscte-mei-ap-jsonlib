package visitor

import models.*

class JSONVisitor: Visitor {

    var jsonString : String = ""

    override fun visit(l: JSONLeaf) {
        jsonString += when(l) {
            is JSONString -> "\"${l.value}\""
            is JSONNumber -> l.value.toString()
            is JSONBoolean -> l.value.toString()
            is JSONNull -> "null"
            else -> ""
        }
    }

    override fun visit(c: JSONComposite): Boolean {
        jsonString += when(c) {
            is JSONArray -> "[ "
            is JSONObject -> "{ "
            else -> ""
        }
        return true
    }

    override fun visit(jsonKeyValuePair: JSONObject.JSONKeyValuePair) {
        jsonString += "\"${jsonKeyValuePair.key}\": "
    }

    override fun endVisit(v: JSONValue) {
        jsonString += ", "
    }

    override fun endVisit(c: JSONComposite) {
        jsonString += when(c) {
            is JSONArray -> " ]"
            is JSONObject -> " }"
            else -> ""
        }
    }

}