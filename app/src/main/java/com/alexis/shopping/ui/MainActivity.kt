package com.alexis.shopping.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.alexis.shopping.ui.navigation.BottomNavigationScreen
import com.alexis.shopping.ui.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigationScreen(navHostController) }
            ) { innerPadding ->
                Navigation(
                    navController = navHostController,
                    innerPadding = innerPadding
                )
            }
        }
    }
}