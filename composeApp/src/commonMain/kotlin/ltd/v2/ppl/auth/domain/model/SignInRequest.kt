package ltd.v2.ppl.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val username: String,
    val password: String,
    val deviceInfo: DeviceInfoRequest,
    val platform: Int

)

@Serializable
data class DeviceInfoRequest(
    val device_id: String = "",
    val app_version: String = "",
    val security_patch: String = "",
    val ui_version: String = "",
    val android_version: String = "",
    val api_version: String = "",
    val manufacture: String = "",
    val user_type: String = "",
    val app_version_code: Int = 0,
    val model: String = "",
    val network_type: String = "",
    val brand: String = ""
)
