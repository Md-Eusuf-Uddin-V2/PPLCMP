package ltd.v2.ppl.auth.di

import com.devx.kdeviceinfo.DeviceInfoXState
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import ltd.v2.ppl.auth.data.repository.LoginInRepoImpl
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.auth.domain.use_case.GetAllCampaignData
import ltd.v2.ppl.auth.domain.use_case.GetCampaignDataById
import ltd.v2.ppl.auth.domain.use_case.InsertCampaignData
import ltd.v2.ppl.auth.domain.use_case.UpdateCampaignData
import ltd.v2.ppl.auth.domain.use_case.getCampaignListData
import ltd.v2.ppl.auth.domain.use_case.getSignInData
import ltd.v2.ppl.auth.domain.use_case.getSurveyModelData
import ltd.v2.ppl.auth.domain.use_case.getUserInfoData
import ltd.v2.ppl.auth.presentation.LoginViewModel
import ltd.v2.ppl.core.data_source.app_pref.AppPreference
import ltd.v2.ppl.core.data_source.local.SurveyDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun loginModule() = module {
    factory { PermissionsControllerFactory(get()) }
    factory { DeviceInfoXState() }
    single { AppPreference(dataStore = get()) }

    single { get<SurveyDatabase>().campaignDataDao }

    single<LogInRepo> {
        LoginInRepoImpl(
            httpClient = get(),
            appPref = get(),
            campaignDataDao = get()
        )
    }
    single<getSignInData> { getSignInData(signInRepo = get()) }
    single<getUserInfoData> { getUserInfoData(loginInRepo = get()) }
    single<getCampaignListData> { getCampaignListData(campRepo = get()) }
    single<getSurveyModelData> { getSurveyModelData(surveyRepo = get()) }


    single<UpdateCampaignData> { UpdateCampaignData(surveyRepo = get()) }
    single<InsertCampaignData> { InsertCampaignData(surveyRepo = get()) }
    single<GetCampaignDataById> { GetCampaignDataById(surveyRepo = get()) }
    single<GetAllCampaignData> { GetAllCampaignData(surveyRepo = get()) }


    viewModel {
        LoginViewModel(
            getAllCampaignData = get(),
            updateCampaignData = get(),
            insertCampData = get(),
            getCampaignDataById = get(),
            getSignInData = get(),
            getUserInfoData = get(),
            getCampaignListData = get(),
            getSurveyDataResponse = get(),
            controller = get(),
            appPref = get(),
            connectivity = get(),
            deviceInfoXState = get()
        )
    }
}