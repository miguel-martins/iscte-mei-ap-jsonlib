package models

import visitor.JSONLeaf

/*
* Represents a JSON String value
* */
data class JSONString(var value: String): JSONLeaf() {
}