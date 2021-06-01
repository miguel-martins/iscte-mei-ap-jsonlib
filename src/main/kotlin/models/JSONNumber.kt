package models

import visitor.JSONLeaf

/*
* Represents a JSON Number value
* */
data class JSONNumber(var value: Number): JSONLeaf() {

}