package ltd.v2.ppl.core.di


import dev.jordond.connectivity.Connectivity
import ltd.v2.ppl.core.data_source.remote.HttpClientFactory
import org.koin.dsl.module


fun commonModule(enableNetworkLogs: Boolean) = module {

    single { Connectivity() }

    single {
        HttpClientFactory.makeClient(enableNetworkLogs = enableNetworkLogs)
    }






}