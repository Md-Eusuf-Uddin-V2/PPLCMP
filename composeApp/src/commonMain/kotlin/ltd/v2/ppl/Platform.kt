package ltd.v2.ppl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform