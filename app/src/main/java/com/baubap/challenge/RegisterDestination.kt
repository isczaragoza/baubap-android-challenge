package com.baubap.challenge

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object RegisterDestination

fun NavController.navigateToRegisterScreen() {
    this.navigate(route = RegisterDestination) {
        graph.findStartDestination().route?.let {
            popUpTo(it)
        }
    }
}

fun NavGraphBuilder.registerScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    onClickRegister: (String, String) -> Unit,
    viewModel: AuthViewModel
) {
    composable<RegisterDestination> {
        RegisterScreen(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToHome = onNavigateToHome,
            onClickRegister = onClickRegister,
            viewModel = viewModel
        )
    }
}
