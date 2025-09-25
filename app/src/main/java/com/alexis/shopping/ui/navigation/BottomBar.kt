package com.alexis.shopping.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexis.shopping.R
import com.alexis.shopping.ui.route.Route

@Composable
fun BottomNavigationScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Route.Home.route } == true,
            onClick = {
                navController.navigate(Route.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(id = R.string.home)
                )
            },
            label = { Text(text = stringResource(id = R.string.home)) }
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Route.Cart.route } == true,
            onClick = {
                navController.navigate(Route.Cart.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = stringResource(id = R.string.cart_icon)
                )
            },
            label = { Text(text = stringResource(id = R.string.cart_icon)) }
        )
    }
}