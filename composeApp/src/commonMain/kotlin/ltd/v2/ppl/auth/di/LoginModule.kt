package ltd.v2.ppl.auth.di

import com.devx.kdeviceinfo.DeviceInfoXState
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import ltd.v2.ppl.auth.data.repository.LoginInRepoImpl
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.auth.domain.use_case.getCampaignListData
import ltd.v2.ppl.auth.domain.use_case.getSignInData
import ltd.v2.ppl.auth.domain.use_case.getUserInfoData
import ltd.v2.ppl.auth.presentation.LoginViewModel
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun loginModule() = module {
    factory { PermissionsControllerFactory(get()) }
    factory { DeviceInfoXState() }
    single { AppPreference(dataStore = get()) }
    single<LogInRepo> { LoginInRepoImpl(httpClient = get(),deviceInfoXState = get(), appPref = get()) }
    single<getSignInData> { getSignInData(signInRepo = get()) }
    single<getUserInfoData> { getUserInfoData(loginInRepo = get()) }
    single<getCampaignListData> { getCampaignListData(campRepo = get()) }
    viewModel { LoginViewModel(getSignInData = get(), getUserInfoData = get(), getCampaignListData = get(), controller = get(), appPref = get(), connectivity = get()) }
}