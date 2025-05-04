package ltd.v2.ppl.common_utils.utils

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

import kotlinx.serialization.json.*

fun convertMapToJsonObject(request: Map<String, Any?>): JsonObject {
    fun convertValue(value: Any?): JsonElement {
        return when (value) {
            null -> JsonNull
            is String -> JsonPrimitive(value)
            is Int -> JsonPrimitive(value)
            is Boolean -> JsonPrimitive(value)
            is Double -> JsonPrimitive(value)
            is Float -> JsonPrimitive(value)
            is Long -> JsonPrimitive(value)
            is Map<*, *> -> {
                JsonObject((value as Map<String, Any?>).mapValues { (_, v) -> convertValue(v) })
            }
            is List<*> -> {
                JsonArray(value.map { convertValue(it) })
            }
            else -> JsonPrimitive(value.toString()) // fallback
        }
    }

    return JsonObject(request.mapValues { (_, v) -> convertValue(v) })
}

