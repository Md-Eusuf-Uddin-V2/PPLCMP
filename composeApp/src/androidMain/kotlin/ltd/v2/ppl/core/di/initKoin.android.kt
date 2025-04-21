package ltd.v2.ppl.core.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import ltd.v2.ppl.core.localization.Localization
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val initTargetModule =  module {
    single<Localization> { Localization(context = androidApplication()) }
    single<HttpClientEngine> { OkHttp.create() }
   // single<DatabaseFactory> { DatabaseFactory(context = androidApplication()) }

}