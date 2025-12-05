package com.miempresa.mowimarket.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(
    navController: NavController
) {
    // Esta pantalla es simple porque los pedidos se muestran en ProfileScreen
    // Redirigir a ProfileScreen
    navController.navigate(com.miempresa.mowimarket.navigation.Routes.Profile.route) {
        popUpTo(navController.graph.startDestinationId)
    }
}
