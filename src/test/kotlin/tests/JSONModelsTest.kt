package tests

import models.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/*
* JSON single values
* */
val nullJSONValue = JSONNull()
val numberJSONValue = JSONNumber(3)
val stringJSONValue = JSONString("string value")
val booleanJSONValue = JSONBoolean(true)

val nullKeyValuePair = JSONObject.JSONKeyValuePair("null", nullJSONValue)
val numberKeyValuePair = JSONObject.JSONKeyValuePair("number", numberJSONValue)
val stringKeyValuePair = JSONObject.JSONKeyValuePair("string", stringJSONValue)
val booleanKeyValuePair = JSONObject.JSONKeyValuePair("boolean", booleanJSONValue)

class JSONModelsTest {


    @Test
    fun testNullJSONValue() {
        assertEquals("JSONNull(value=null)", nullJSONValue.toString())
    }

    @Test
    fun testNumberJSONValue() {
        assertEquals("JSONNumber(value=3)", numberJSONValue.toString())
    }

    @Test
    fun testStringJSONValue() {
        assertEquals("JSONString(value=string value)", stringJSONValue.toString())
    }

    @Test
    fun testBooleanJSONValue() {
        assertEquals("JSONBoolean(value=true)", booleanJSONValue.toString())
    }

    @Test
    fun testNullKeyValuePair() {
        assertEquals("JSONKeyValuePair(key=null, value=JSONNull(value=null))", nullKeyValuePair.toString())
    }

    @Test
    fun testNumberKeyValuePair() {
        assertEquals("JSONKeyValuePair(key=number, value=JSONNumber(value=3))", numberKeyValuePair.toString())
    }

    @Test
    fun testStringKeyValuePair() {
        assertEquals("JSONKeyValuePair(key=string, value=JSONString(value=string value))", stringKeyValuePair.toString())
    }

    @Test
    fun testBooleanKeyValuePair() {
        assertEquals("JSONKeyValuePair(key=boolean, value=JSONBoolean(value=true))", booleanKeyValuePair.toString())
    }

}