package ltd.v2.ppl.app

import platform.Foundation.NSBundle
import platform.UIKit.UIApplication

actual class ContextFactory {

    actual fun getContext(): Any = NSBundle

    actual fun getApplication(): Any = UIApplication

    actual fun getActivity(): Any = UIApplication.sharedApplication.keyWindow?.rootViewController ?: ""
}