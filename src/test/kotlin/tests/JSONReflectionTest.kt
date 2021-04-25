package tests

import generator.JSONExclude
import generator.JSONGenerator
import generator.JSONIdentifier
import visitor.JSONVisitor

//class JSONReflectionTest {}

data class JSONClass(@JSONExclude val nullValue: Nothing?, @JSONIdentifier("stringId") val stringValue: String, val numberValue: Number, val booleanValue: Boolean, val type: CustomType, val child: JSONClass? = null)
enum class CustomType {
    CustomType1, CustomType2, CustomType3
}

fun main() {

    val json = JSONClass(null,"String Value", 6, false, CustomType.CustomType1)
    val json2 = JSONClass(null,"String Value", 6, false, CustomType.CustomType1, json)
    val jsonGenerator = JSONGenerator()
    val jsonSerializer = JSONVisitor()
    jsonGenerator.buildFrom(json2).accept(jsonSerializer)
    println(jsonSerializer.jsonString)

    val customList = mutableListOf<Any>(1,true, "hello", json)
    val jsonSerializer2 = JSONVisitor()
    jsonGenerator.buildFrom(customList).accept(jsonSerializer2)
    println(jsonSerializer2.jsonString)
}