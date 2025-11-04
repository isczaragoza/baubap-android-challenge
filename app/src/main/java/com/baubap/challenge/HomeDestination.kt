package com.baubap.challenge

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

fun NavController.navigateToHomeScreen() {
    this.navigate(route = HomeDestination) {
        graph.findStartDestination().route?.let {
            popUpTo(it)
        }
    }
}

fun NavGraphBuilder.homeScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel
) {
    composable<HomeDestination> {
        HomeScreen(
            onLogout = onLogout,
            viewModel = viewModel
        )
    }
}
