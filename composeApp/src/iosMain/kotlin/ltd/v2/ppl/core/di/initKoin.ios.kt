package ltd.v2.ppl.core.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import ltd.v2.ppl.core.data_source.local.DatabaseFactory
import ltd.v2.ppl.core.localization.Localization
import org.koin.dsl.module

actual val initTargetModule =  module {
    single <Localization>{ Localization() }
    single<HttpClientEngine> { Darwin.create() }
    single { DatabaseFactory()  }
}