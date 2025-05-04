package ltd.v2.ppl.core.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.jordond.connectivity.Connectivity
import ltd.v2.ppl.core.data_source.local.DatabaseFactory
import ltd.v2.ppl.core.data_source.local.SurveyDatabase
import ltd.v2.ppl.core.data_source.remote.HttpClientFactory
import org.koin.dsl.module


fun commonModule(enableNetworkLogs: Boolean) = module {

    single { Connectivity() }

    single {
        HttpClientFactory.makeClient(enableNetworkLogs = enableNetworkLogs)
    }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }


}