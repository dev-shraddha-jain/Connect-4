package com.groot.connect4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.groot.connect4.ui.screen.GameConfigScreen
import com.groot.connect4.ui.screen.GameScreen
import com.groot.connect4.ui.screen.SplashScreen

fun Navigate(navController: NavHostController, wrapperDto: Any, destination: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("navArgWrapperDto", wrapperDto)
    navController.navigate(destination)
}

fun getNavArg(navController: NavHostController): NavArgWrapperDto? {
    return navController.previousBackStackEntry?.savedStateHandle?.get<NavArgWrapperDto>("navArgWrapperDto")
}

@Composable
fun NavHostController(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Route.splashScreen) {
        composable(Route.splashScreen) {
            SplashScreen(navController)
        }

        composable(Route.gameConfigScreen) {
            GameConfigScreen(navController)
        }

        composable(Route.gameScreen) {
            GameScreen(navController)
        }


    }

}