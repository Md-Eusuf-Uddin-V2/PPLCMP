package ltd.v2.ppl.common_utils.utils

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun convertMapToJsonObject(request: Map<String, Any>): JsonObject {
    val jsonObject = JsonObject(request.mapValues { entry ->
        when (val value = entry.value) {
            is String -> JsonPrimitive(value)
            is Int -> JsonPrimitive(value)
            is Boolean -> JsonPrimitive(value)
            is Map<*, *> -> JsonObject((value as Map<String, Any>).mapValues { JsonPrimitive(it.value.toString()) })
            else -> JsonPrimitive(value.toString())
        }
    })
    return jsonObject
}