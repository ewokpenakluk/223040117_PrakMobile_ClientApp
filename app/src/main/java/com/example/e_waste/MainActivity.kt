package com.example.e_waste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_waste.presentation.ui.Screen.Auth.ForgotPassword.ForgotPasswordScreen
import com.example.e_waste.presentation.ui.Screen.Auth.Login.LoginScreen
import com.example.e_waste.presentation.ui.Screen.Home.MainScreen
import com.example.e_waste.presentation.ui.Screen.Auth.Otp.OtpVerificationScreen
import com.example.e_waste.presentation.ui.Screen.User.Profile.ProfileScreen
import com.example.e_waste.presentation.ui.Screen.Auth.Register.RegisterScreen
import com.example.e_waste.presentation.ui.Screen.Auth.ResetPassword.ResetPasswordScreen
import com.example.e_waste.presentation.ui.theme.EwasteTheme
import dagger.hilt.android.AndroidEntryPoint


// MainActivity.kt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EwasteTheme {
                EWasteApp()
            }
        }
    }
}

@Composable
fun EWasteApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToForgotPassword = { navController.navigate("forgot_password") },
                onLoginSuccess = { navController.navigate("main") },
                onForgotPassword = { navController.navigate("forgot_password") }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onRegisterSuccess = { navController.navigate("otp_verification/$it") }
            )
        }
        composable("otp_verification/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpVerificationScreen(
                email = email,
                onVerificationSuccess = { navController.navigate("login") }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onResetEmailSent = { email -> navController.navigate("reset_password/$email") }
            )
        }
        composable("reset_password/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ResetPasswordScreen(
                email = email,
                onPasswordResetSuccess = { navController.navigate("login") }
            )
        }
        composable("main") {
            MainScreen(
                onNavigateToProfile = { navController.navigate("profile") },
                onLogout = { navController.navigate("login") }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}