package com.jakey.aetnacompose.presentation.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jakey.aetnacompose.presentation.SearchState
import com.jakey.aetnacompose.presentation.screens.Screen

@Composable
fun SearchListVerticalGrid(
    state: SearchState,
    text: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            state.searchResults?.let { list ->
                if (text.isBlank()) {
                    list.toMutableStateList().clear()
                } else {
                    items(list.size) { index ->
                        SearchItem(
                            image = list[index].imageUrl,
                            title = list[index].title,
                            query = text,
                            onClick = {
                                navController.navigate(Screen.DetailScreen.route)
                            }
                        )
                    }
                }
            }
        }
    )
}