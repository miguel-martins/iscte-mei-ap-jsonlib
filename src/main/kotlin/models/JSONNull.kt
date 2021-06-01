package models

import visitor.JSONLeaf

/*
* Represents a JSON Null value
* */
data class JSONNull(val value: Nothing? = null) : JSONLeaf() {
}