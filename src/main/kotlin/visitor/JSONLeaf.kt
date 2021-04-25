package visitor

/*
* Leaf Element in the Visitor pattern
* */
abstract class JSONLeaf() : JSONValue() {
    abstract val value: Any?

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}