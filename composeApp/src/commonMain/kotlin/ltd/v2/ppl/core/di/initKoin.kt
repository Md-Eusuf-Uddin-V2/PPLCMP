package ltd.v2.ppl.core.di

import ltd.v2.ppl.attendance.di.attendanceModule
import ltd.v2.ppl.auth.di.loginModule
import ltd.v2.ppl.splash.di.splashModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect val initTargetModule: Module

fun initKoin(enableNetworkLogs: Boolean = true, config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            initTargetModule,
            commonModule(enableNetworkLogs = enableNetworkLogs),
            splashModule(),
            loginModule(),
            attendanceModule()
        )
    }
}