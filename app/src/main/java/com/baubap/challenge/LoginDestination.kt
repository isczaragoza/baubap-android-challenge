package com.baubap.challenge

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**Seguridad en tiempo de compilacion*/
@Serializable
object LoginDestination

fun NavController.navigateToLoginScreen() {
    this.navigate(route = LoginDestination) {
        graph.findStartDestination().route?.let {
            popUpTo(it)
        }
    }
}

fun NavGraphBuilder.loginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    onLoginError: (String?) -> Unit,
    viewModel: AuthViewModel
) {
    composable<LoginDestination> {
        LoginScreen(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToHome = onNavigateToHome,
            onLoginError = onLoginError,
            viewModel = viewModel
        )
    }
}


/**Ejemplo del uso de navegación con tipos seguros*/

/**
 * @Serializable data class Product(val id: String)
 *
 * */


/**En el Navigation Graph
 *
 * composable<Product> { backStackEntry ->
 *   val product : Product = backStackEntry.toRoute()
 *   ProductScreen(product)
 * }
 *
 * */

/**
 * En navcontroller
 *
 * navController.navigate(route = Product(id = "ABC"))
 *
 *
 * */

/**
 * Ejemplo completo:
 *
 * NavHost(
 *     navController = navController,
 *     startDestination = Home
 * ) {
 *     composable<Home> {
 *         HomeScreen(
 *             onProductClick = { id ->
 *                 navController.navigate(route = Product(id))
 *             }
 *         )
 *     }
 *     composable<Product> { backStackEntry ->
 *         val product : Product = backStackEntry.toRoute()
 *         ProductScreen(product)
 *     }
 * }
 *
 *
 * **/