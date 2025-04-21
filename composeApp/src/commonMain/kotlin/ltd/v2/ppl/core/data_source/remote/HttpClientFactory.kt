package ltd.v2.ppl.core.data_source.remote

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ltd.v2.ppl.core.app_url.AppUrl

object HttpClientFactory {
    fun makeClient(enableNetworkLogs: Boolean): HttpClient {
        return HttpClient {
            // Set default request configuration
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = AppUrl.BASE_URL
                }
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
            }

            // Timeout configuration for requests
            install(HttpTimeout) {
                requestTimeoutMillis = 80_000 // 15 seconds for request timeout
                connectTimeoutMillis = 45_000 // 10 seconds for connection timeout
                socketTimeoutMillis = 45_000 // 15 seconds for socket timeout
            }

            // Retry configuration with exponential backoff
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
                exponentialDelay(base = 200.0, maxDelayMs = 5000L)
            }



            // Content negotiation setup for JSON parsing
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                })
            }

            // Logging for network requests and responses
            if (enableNetworkLogs) {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.i(tag = "Http Client", message = message)
                        }
                    }
                }
                Napier.base(DebugAntilog()) // Initialize Napier logging
            }





            // Error handling for responses
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, request ->
                    Napier.e("HTTP Error for request: ${request.url}", cause)
                }
            }
        }
    }
}
