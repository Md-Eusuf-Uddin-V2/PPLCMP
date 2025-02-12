package ltd.v2.ppl.core.di

import ltd.v2.ppl.core.localization.Localization
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule =  module {
    single<Localization> { Localization(context = androidContext()) }
}