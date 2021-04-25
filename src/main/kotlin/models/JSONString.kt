package models

import visitor.JSONLeaf

/*
* Represents a JSON String value
* */
data class JSONString(override val value: String): JSONLeaf() {
}