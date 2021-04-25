package models

import visitor.JSONLeaf

/*
* Represents a JSON Number value
* */
data class JSONNumber(override val value: Number): JSONLeaf() {

}