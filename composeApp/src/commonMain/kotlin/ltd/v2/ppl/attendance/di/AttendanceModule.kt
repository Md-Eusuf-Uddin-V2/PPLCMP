package ltd.v2.ppl.attendance.di

import com.devx.kdeviceinfo.DeviceInfoXState
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import ltd.v2.ppl.attendance.presentation.AttendanceScreenViewModel
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


fun attendanceModule() = module {
    viewModel { AttendanceScreenViewModel() }
}