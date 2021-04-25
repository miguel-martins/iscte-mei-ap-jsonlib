package models

import visitor.JSONLeaf

/*
* Represents a JSON Boolean value
* */
data class JSONBoolean(override val value: Boolean) : JSONLeaf() {

}