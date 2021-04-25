package generator

import models.*
import visitor.JSONValue
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class JSONGenerator() {

    fun buildFrom(from: Any?): JSONValue {
        return when(from) {
            null -> JSONNull()
            is Number -> JSONNumber(from)
            is String -> JSONString(from)
            is Boolean -> JSONBoolean(from)
            is Enum<*> -> JSONString(from.name)
            // List and Set become a JSONArray
            is List<*> -> JSONArray(from.map { it -> buildFrom(it) }.toMutableList())
            is Set<*> -> JSONArray(from.map { it -> buildFrom(it) }.toMutableList())
            // Map becomes a JSONObject
            is Map<*, *> -> JSONObject(from.entries.map { JSONObject.JSONKeyValuePair(it.key.toString(), buildFrom(it.value)) }.toMutableList())
            // Else it defaults to treating the parameter as an object
            else -> {
                val clazz: KClass<*> = from::class as KClass<*>
                val keypairList: MutableList<JSONObject.JSONKeyValuePair> = clazz.declaredMemberProperties.filter { !it.hasAnnotation<JSONExclude>() }.map { JSONObject.JSONKeyValuePair(if(it.hasAnnotation<JSONIdentifier>()) it.findAnnotation<JSONIdentifier>()!!.identifier else it.name, buildFrom(it.getter.call(from))) }.toMutableList()
                return JSONObject(keypairList)
            }
        }
    }

}