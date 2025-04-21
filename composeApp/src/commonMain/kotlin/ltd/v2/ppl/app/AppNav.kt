package ltd.v2.ppl.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ltd.v2.ppl.auth.presentation.LoginScreenRoot
import ltd.v2.ppl.auth.presentation.PermissionsViewModel
import ltd.v2.ppl.splash.SplashScreenRoot
import ltd.v2.ppl.splash.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Splash) {

        composable<Route.Splash> {
            val viewModel = koinViewModel<SplashViewModel>()
            SplashScreenRoot(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Route.Login)
                }
            )
        }

        composable<Route.Login> {
           // val viewmodel = koinViewModel<PermissionsViewModel>()
            LoginScreenRoot(
            )
        }
    }

}