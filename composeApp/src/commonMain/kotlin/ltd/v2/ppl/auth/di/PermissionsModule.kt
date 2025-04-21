package ltd.v2.ppl.auth.di

import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import ltd.v2.ppl.auth.presentation.PermissionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun permissionsModule() = module{
    viewModel { PermissionsViewModel(get()) }
}