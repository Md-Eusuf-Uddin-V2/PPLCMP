package ltd.v2.ppl.core.di


import ltd.v2.ppl.core.data_source.remote.HttpClientFactory
import org.koin.dsl.module


fun commonModule(enableNetworkLogs: Boolean) = module {

    single {
        HttpClientFactory.makeClient(enableNetworkLogs = enableNetworkLogs)
    }






}