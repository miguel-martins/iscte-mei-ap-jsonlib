package models

import visitor.JSONComposite
import visitor.JSONValue
import visitor.Visitor

data class JSONObject(val values: MutableList<JSONKeyValuePair>): JSONComposite() {
    data class JSONKeyValuePair(val key: String, val value: JSONValue): JSONValue(){
        override fun accept(v: Visitor) {
            v.visit(this)
            value.accept(v)
        }
    }

    override fun accept(v: Visitor) {
        if(v.visit(this))
            values.forEach {
                it.accept(v)
                if(values.last() != it)
                    v.endVisit(it)

            }
        v.endVisit(this)
    }

}