package ltd.v2.ppl.auth.di

import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import ltd.v2.ppl.auth.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun loginModule() = module {
    factory { PermissionsControllerFactory(get()) }
    viewModel { LoginViewModel(get(), get()) }
}