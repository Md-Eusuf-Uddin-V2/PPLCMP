package ltd.v2.ppl.core.di

import coil3.PlatformContext
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import ltd.v2.ppl.app.ContextFactory
import ltd.v2.ppl.core.data_source.app_pref.createDataStore
import ltd.v2.ppl.core.localization.Localization
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.math.sin

actual val initTargetModule =  module {
    single<ContextFactory> { ContextFactory(get()) }
    single<Localization> { Localization(context = androidApplication()) }
    single { createDataStore(androidContext())}
    single<HttpClientEngine> { OkHttp.create() }
}