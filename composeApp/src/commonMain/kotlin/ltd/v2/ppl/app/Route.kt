package ltd.v2.ppl.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Splash: Route

    @Serializable
    data object Login: Route

    @Serializable
    data object Dashboard: Route

    @Serializable
    data object Attendance: Route
}