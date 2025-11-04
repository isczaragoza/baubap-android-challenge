package com.baubap.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.baubap.challenge.ui.theme.BaubapChallengeTheme
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaubapChallengeTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                var loginError: String? by rememberSaveable { mutableStateOf(null) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    val authViewModel: AuthViewModel = viewModel()
                    val authState by authViewModel.collectAsState()
                    AuthApp(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        onLoginError = { error ->
                            loginError = error
                        }
                    )
                    LaunchedEffect(key1 = authState.errorMessage, key2 = loginError) {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        if (authState.errorMessage != null && loginError != null) {
                            val notNullLoginError = loginError ?: "Error de Login"
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = notNullLoginError,
                                    withDismissAction = false,
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                            return@LaunchedEffect
                        }
                        if (authState.errorMessage != null) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = authState.errorMessage.toString(),
                                    actionLabel = "Cerrar"
                                )
                            }
                            return@LaunchedEffect
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AuthApp(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    onLoginError: (String?) -> Unit
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        loginScreen(
            onNavigateToRegister = {
                navController.navigateToRegisterScreen()
            },
            onNavigateToHome = {
                navController.navigateToHomeScreen()
            },
            onLoginError = onLoginError,
            viewModel = authViewModel
        )
        registerScreen(
            onNavigateToLogin = { navController.navigateToLoginScreen() },
            onNavigateToHome = { navController.navigateToHomeScreen() },
            onClickRegister = { email, password ->
                authViewModel.register(email, password)
            },
            viewModel = authViewModel
        )
        homeScreen(
            onLogout = { navController.navigateToLoginScreen() },
            viewModel = authViewModel
        )
    }
}
