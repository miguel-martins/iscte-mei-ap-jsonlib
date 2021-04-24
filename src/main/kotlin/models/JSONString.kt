package models

import visitor.JSONLeaf

/*
* Represents a JSON String value
* */
class JSONString(override val value: String): JSONLeaf() {
}