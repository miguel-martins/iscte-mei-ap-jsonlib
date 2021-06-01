package visitor

/*
* Leaf Element in the Visitor pattern
* */
abstract class JSONLeaf() : JSONValue() {
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}