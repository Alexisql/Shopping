package com.alexis.shopping.ui.route

sealed class Route(val route: String) {
    data object Home : Route("Home")
    data object Cart : Route("Cart")
    data object Failure : Route("failure/{message}") {
        fun createRoute(message: String) = "failure/$message"
    }
}