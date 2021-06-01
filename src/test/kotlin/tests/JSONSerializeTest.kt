package tests

import models.*
import org.junit.jupiter.api.Test
import visitor.JSONConditionedVisitor
import visitor.JSONVisitor
import kotlin.test.assertEquals

val nullValue = JSONNull()
val numberValue = JSONNumber(3)
val numberValue2 = JSONNumber(4)
val stringValue = JSONString("string value")
val booleanValue = JSONBoolean(true)

var nullKeyValue = JSONObject.JSONKeyValuePair("null", nullValue)
var numberKeyValue = JSONObject.JSONKeyValuePair("number", numberValue)
var numberKeyValue2 = JSONObject.JSONKeyValuePair("number2", numberValue2)
var stringKeyValue = JSONObject.JSONKeyValuePair("string", stringValue)
val booleanKeyValue = JSONObject.JSONKeyValuePair("boolean", booleanValue)

var jsonObject = JSONObject(mutableListOf(nullKeyValue, numberKeyValue, numberKeyValue2, stringKeyValue, booleanKeyValue))
var jsonArray = JSONArray(mutableListOf(nullValue, numberValue, numberValue2, stringValue, booleanValue, jsonObject))

var jsonObjectSerializer = JSONVisitor()
var jsonArraySerializer = JSONVisitor()

var jsonObjectConditioned = JSONConditionedVisitor() {it -> it is JSONNumber}
var jsonObjectConditioned2 = JSONConditionedVisitor() {it -> it is JSONNumber && it.value == 3 }





class JSONSerializeTest {

    @Test
    fun testJsonObject(){
        jsonObject.accept(jsonObjectSerializer)
        assertEquals("{ \"null\": null, \"number\": 3, \"number2\": 4, \"string\": \"string value\", \"boolean\": true }", jsonObjectSerializer.jsonString)
    }

    @Test
    fun testJsonArray(){
        jsonArray.accept(jsonArraySerializer)
        assertEquals("[ null, 3, 4, \"string value\", true, { \"null\": null, \"number\": 3, \"number2\": 4, \"string\": \"string value\", \"boolean\": true } ]", jsonArraySerializer.jsonString)
    }


    @Test
    fun testJsonObjectConditioned(){
        jsonObject.accept(jsonObjectConditioned)
        assertEquals("[JSONNumber(value=3), JSONNumber(value=4)]", jsonObjectConditioned.values.toString())
    }

    @Test
    fun testJsonObjectConditioned2(){
        jsonObject.accept(jsonObjectConditioned2)
        assertEquals("[JSONNumber(value=3)]", jsonObjectConditioned2.values.toString())
    }

}