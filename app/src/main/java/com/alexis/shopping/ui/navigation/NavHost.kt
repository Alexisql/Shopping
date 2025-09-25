package com.alexis.shopping.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alexis.shopping.ui.cart.CartScreen
import com.alexis.shopping.ui.core.ErrorScreen
import com.alexis.shopping.ui.home.HomeScreen
import com.alexis.shopping.ui.route.Route

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Route.Home.route) {
            HomeScreen(
                modifier = Modifier,
                navController = navController
            )
        }
        composable(route = Route.Cart.route) {
            CartScreen()
        }
        composable(
            route = Route.Failure.route,
            arguments = listOf(
                navArgument("message") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ErrorScreen(
                modifier = Modifier,
                message = backStackEntry.arguments?.getString("message") ?: ""
            )
        }
    }
}
