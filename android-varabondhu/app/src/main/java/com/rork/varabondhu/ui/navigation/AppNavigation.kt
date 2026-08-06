package com.rork.varabondhu.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rork.varabondhu.ui.screens.HomeScreen
import com.rork.varabondhu.ui.screens.LoginScreen
import com.rork.varabondhu.ui.screens.SignUpScreen
import com.rork.varabondhu.ui.screens.SplashScreen

private object Route {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val SIGN_UP = "signUp"
    const val HOME = "home"
}

private const val TRANSITION_MILLIS = 300

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.SPLASH,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION_MILLIS)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION_MILLIS)) }
    ) {
        composable(Route.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Route.LOGIN) {
                        popUpTo(Route.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Route.LOGIN,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_MILLIS)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_MILLIS)) }
        ) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.HOME) {
                        popUpTo(Route.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Route.SIGN_UP) { launchSingleTop = true }
                }
            )
        }

        composable(
            route = Route.SIGN_UP,
            enterTransition = {
                slideInHorizontally(animationSpec = tween(TRANSITION_MILLIS)) { it / 3 } +
                    fadeIn(animationSpec = tween(TRANSITION_MILLIS))
            },
            exitTransition = {
                slideOutHorizontally(animationSpec = tween(TRANSITION_MILLIS)) { it / 3 } +
                    fadeOut(animationSpec = tween(TRANSITION_MILLIS))
            },
            popExitTransition = {
                slideOutHorizontally(animationSpec = tween(TRANSITION_MILLIS)) { it / 3 } +
                    fadeOut(animationSpec = tween(TRANSITION_MILLIS))
            }
        ) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Route.HOME) {
                        popUpTo(Route.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Route.LOGIN) { launchSingleTop = true }
                    }
                }
            )
        }

        composable(Route.HOME) {
            HomeScreen()
        }
    }
}
