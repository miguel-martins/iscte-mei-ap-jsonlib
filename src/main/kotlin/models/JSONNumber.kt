package models

import visitor.JSONLeaf

/*
* Represents a JSON Number value
* */
class JSONNumber(override val value: Number): JSONLeaf() {

}