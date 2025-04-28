package ltd.v2.ppl.core.domain

sealed interface DataError : Error {
    val message: String
}

data class RemoteError(override val message: String) : DataError
data class LocalError(override val message: String) : DataError