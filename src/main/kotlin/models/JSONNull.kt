package models

import visitor.JSONLeaf

/*
* Represents a JSON Null value
* */
class JSONNull(override val value: Nothing) : JSONLeaf() {
}