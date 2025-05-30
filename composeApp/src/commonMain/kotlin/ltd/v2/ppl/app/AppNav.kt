package ltd.v2.ppl.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import ltd.v2.ppl.attendance.presentation.attendance_root.AttendanceScreenRoot
import ltd.v2.ppl.attendance.presentation.attendance_root.AttendanceScreenViewModel
import ltd.v2.ppl.auth.presentation.LoginScreenRoot
import ltd.v2.ppl.auth.presentation.LoginViewModel
import ltd.v2.ppl.splash.SplashScreenRoot
import ltd.v2.ppl.splash.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Splash) {

        composable<Route.Splash> {
            val viewModel = koinViewModel<SplashViewModel>()
            SplashScreenRoot(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Route.Login) {
                        popUpTo(Route.Splash) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Route.Login> {
            val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
            val controller = remember(factory) {
                factory.createPermissionsController()
            }

            val viewModel: LoginViewModel = koinViewModel(parameters = { parametersOf(controller) })

            LoginScreenRoot(
                viewModel = viewModel,
                controller = controller,
                navigateToDashboard = {
                    navController.navigate(Route.Dashboard)
                },
                navigateToAttendance = {
                    navController.navigate(Route.Attendance)/*{
                        popUpTo(Route.Login) {
                            inclusive = true
                        }
                    }*/
                }
            )
        }

        composable<Route.Dashboard> {
            // DashboardScreenRoot()
        }

        composable<Route.Attendance> {
            val viewModel: AttendanceScreenViewModel = koinViewModel<AttendanceScreenViewModel>()
            AttendanceScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

}