package models

import visitor.JSONLeaf

/*
* Represents a JSON Boolean value
* */
class JSONBoolean(override val value: Boolean) : JSONLeaf() {

}