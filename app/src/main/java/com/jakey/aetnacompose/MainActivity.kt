package com.jakey.aetnacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jakey.aetnacompose.presentation.NavGraphs
import com.jakey.aetnacompose.ui.theme.AetnaComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AetnaComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    DestinationsNavHost(navGraph = NavGraphs.root)
//                    NavHost(
//                        navController = navController,
//                        startDestination = Screen.SearchScreen.route
//                    ) {
//                        composable(route = Screen.SearchScreen.route) {
//                            SearchScreen(
//                                state = state,
//                                viewModel = viewModel,
//                                context = this@MainActivity,
//                                navController = navController
//                            )
//                        }
//                        composable(route = Screen.DetailScreen.route) {
//                            DetailScreen()
//                        }
//                    }

                }
            }

        }
    }


}


