package com.jakey.aetnacompose.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jakey.aetnacompose.R
import com.jakey.aetnacompose.presentation.ImageListViewModel
import com.jakey.aetnacompose.presentation.SearchState
import com.jakey.aetnacompose.presentation.composables.Loader
import com.jakey.aetnacompose.presentation.composables.SearchListVerticalGrid

@Composable
fun SearchScreen(
    state: SearchState,
    viewModel: ImageListViewModel,
    navController: NavController,
    context: Context
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = SnackbarHostState()
    )
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Aetna Compose"
                    )
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
        Column() {

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.onSearch(text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(text = "Search...")
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                ),
                trailingIcon = {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            text = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear search field"
                            )
                        }

                    }
                },
                maxLines = 1,
                singleLine = true
            )
            if (state.isLoading == true) {
                Loader(
                    state = state,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            SearchListVerticalGrid(
                state = state,
                text = text,
                navController = navController,
                modifier = Modifier.weight(1f)
            )
        }
    }
}