package ltd.v2.ppl.splash.di

import ltd.v2.ppl.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun splashModule() = module{
    viewModel { SplashViewModel() }
}