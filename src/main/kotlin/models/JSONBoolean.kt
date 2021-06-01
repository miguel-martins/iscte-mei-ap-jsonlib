package models

import visitor.JSONLeaf

/*
* Represents a JSON Boolean value
* */
data class JSONBoolean(var value: Boolean) : JSONLeaf() {

}