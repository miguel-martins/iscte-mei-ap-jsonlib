package tests

import generator.JSONExclude
import generator.JSONGenerator
import generator.JSONIdentifier
import org.junit.jupiter.api.Test
import visitor.JSONVisitor
import kotlin.test.assertEquals

//class JSONReflectionTest {}

data class JSONClass(@JSONExclude val nullValue: Nothing?, @JSONIdentifier("stringId") val stringValue: String, val numberValue: Number, val booleanValue: Boolean, val type: CustomType, val child: JSONClass? = null)
enum class CustomType {
    CustomType1, CustomType2, CustomType3
}

val json = JSONClass(null,"String Value", 6, false, CustomType.CustomType1)
val json2 = JSONClass(null,"String Value", 6, false, CustomType.CustomType1, json)
val jsonGenerator = JSONGenerator()
val jsonSerializer = JSONVisitor()

val customList = mutableListOf(1,null,true, "hello", json)
val jsonSerializer2 = JSONVisitor()

class JSONReflectionTest {

    @Test
    fun testObjectGeneration(){
        jsonGenerator.buildFrom(json2).accept(jsonSerializer)
        assertEquals("{ \"booleanValue\": false, \"child\": { \"booleanValue\": false, \"child\": null, \"numberValue\": 6, \"stringId\": \"String Value\", \"type\": \"CustomType1\" }, \"numberValue\": 6, \"stringId\": \"String Value\", \"type\": \"CustomType1\" }", jsonSerializer.jsonString)
    }

    @Test
    fun testArrayGeneration(){
        jsonGenerator.buildFrom(customList).accept(jsonSerializer2)
        assertEquals("[ 1, null, true, \"hello\", { \"booleanValue\": false, \"child\": null, \"numberValue\": 6, \"stringId\": \"String Value\", \"type\": \"CustomType1\" } ]", jsonSerializer2.jsonString)
    }
}