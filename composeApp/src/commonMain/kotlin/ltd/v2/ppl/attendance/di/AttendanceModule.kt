package ltd.v2.ppl.attendance.di

import ltd.v2.ppl.attendance.data.repository.AttendanceRepoImpl
import ltd.v2.ppl.attendance.domain.repositories.AttendanceRepo
import ltd.v2.ppl.attendance.domain.use_case.GetMaterialsData
import ltd.v2.ppl.attendance.domain.use_case.PostMaterialsData
import ltd.v2.ppl.attendance.presentation.asset.AssetsViewModel
import ltd.v2.ppl.attendance.presentation.attendance_root.AttendanceScreenViewModel
import ltd.v2.ppl.attendance.presentation.giveable.GiveableViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun attendanceModule() = module {
    single<AttendanceRepo> {
        AttendanceRepoImpl(
            httpClient = get(),
            appPref = get(),
        )
    }

    factory<GetMaterialsData> {
        GetMaterialsData(
            attendanceRepo = get()
        )
    }

    viewModel {
        AttendanceScreenViewModel(
            getMaterialsData = get(),
            appPref = get(),
            connectivity = get()
        )
    }

    viewModel { AssetsViewModel(appPref = get()) }

    factory<PostMaterialsData> {
        PostMaterialsData(attendanceRepo = get())
    }

    viewModel { GiveableViewModel(appPref = get(), postMaterialsData = get()) }


}