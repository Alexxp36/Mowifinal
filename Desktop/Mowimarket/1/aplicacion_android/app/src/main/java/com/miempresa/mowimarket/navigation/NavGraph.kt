package com.miempresa.mowimarket.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.miempresa.mowimarket.ui.screens.auth.LoginScreen
import com.miempresa.mowimarket.ui.screens.auth.RegisterScreen
import com.miempresa.mowimarket.ui.screens.user.*
import com.miempresa.mowimarket.ui.screens.admin.*

/**
 * Grafo de navegación principal
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ========== AUTENTICACIÓN ==========
        composable(Routes.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.Register.route)
                },
                onLoginSuccess = { isAdmin ->
                    val destination = if (isAdmin) {
                        Routes.AdminHome.route
                    } else {
                        Routes.UserHome.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Routes.UserHome.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // ========== PANTALLAS DE USUARIO ==========
        composable(Routes.UserHome.route) {
            UserHomeScreen(
                navController = navController,
                onLogout = onLogout
            )
        }

        composable(Routes.ProductList.route) {
            ProductListScreen(
                navController = navController
            )
        }

        composable(
            route = Routes.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                navController = navController
            )
        }

        composable(Routes.Cart.route) {
            CartScreen(
                navController = navController
            )
        }

        composable(Routes.Checkout.route) {
            CheckoutScreen(
                navController = navController
            )
        }

        composable(Routes.MyOrders.route) {
            MyOrdersScreen(
                navController = navController
            )
        }

        composable(
            route = Routes.OrderDetail.route,
            arguments = listOf(
                navArgument("orderId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getInt("orderId") ?: 0
            OrderDetailScreen(
                orderId = orderId,
                navController = navController
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                navController = navController,
                onLogout = onLogout
            )
        }

        // ========== PANTALLAS DE ADMINISTRADOR ==========
        composable(Routes.AdminHome.route) {
            AdminHomeScreen(
                navController = navController,
                onLogout = onLogout
            )
        }

        composable(Routes.AdminProducts.route) {
            AdminProductsScreen(
                navController = navController
            )
        }

        composable(Routes.AdminOrders.route) {
            AdminOrdersScreen(
                navController = navController
            )
        }

        composable(Routes.AdminUsers.route) {
            AdminUsersScreen(
                navController = navController
            )
        }

        composable(Routes.AdminStats.route) {
            AdminStatsScreen(
                navController = navController
            )
        }
    }
}
