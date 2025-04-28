package ltd.v2.ppl

import android.os.Build


class AndroidPlatform : Platform {
    override val name: String = Build.VERSION.RELEASE
}

actual fun getPlatform(): Platform = AndroidPlatform()
