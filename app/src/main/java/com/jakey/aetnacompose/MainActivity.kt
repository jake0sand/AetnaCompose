package com.jakey.aetnacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakey.aetnacompose.presentation.ImageListViewModel
import com.jakey.aetnacompose.presentation.screens.DetailScreen
import com.jakey.aetnacompose.presentation.screens.Screen
import com.jakey.aetnacompose.presentation.screens.SearchScreen
import com.jakey.aetnacompose.ui.theme.AetnaComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ImageListViewModel = hiltViewModel()
            val navController = rememberNavController()
            AetnaComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state = viewModel.state
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SearchScreen.route
                    ) {
                        composable(route = Screen.SearchScreen.route) {
                            SearchScreen(
                                state = state,
                                viewModel = viewModel,
                                context = this@MainActivity,
                                navController = navController
                            )
                        }
                        composable(route = Screen.DetailScreen.route) {
                            DetailScreen()
                        }
                    }

                }
            }

        }
    }


}


