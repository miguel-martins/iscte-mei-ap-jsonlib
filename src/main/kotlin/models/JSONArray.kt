package models

import visitor.JSONComposite
import visitor.JSONValue
import visitor.Visitor


class JSONArray(val values: MutableList<JSONValue>): JSONComposite() {

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