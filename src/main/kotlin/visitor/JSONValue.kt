package visitor

/*
* Corresponds to the Element class in the Visitor pattern.
* */
abstract class JSONValue() {
    abstract fun accept(v: Visitor)
}