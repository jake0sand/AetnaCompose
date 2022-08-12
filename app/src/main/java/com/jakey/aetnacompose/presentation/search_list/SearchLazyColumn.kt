package com.jakey.aetnacompose.presentation.search_list

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.jakey.aetnacompose.presentation.composables.SearchItem
import com.jakey.aetnacompose.presentation.destinations.DetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SearchLazyColumn(
    state: SearchState,
    viewModel: ImageListViewModel,
    navigator: DestinationsNavigator
) {
    if (state.searchResults != null) {

        state.searchResults.let { list ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(list.size) { index ->
                    val item = list[index]

                    SearchItem(
                        image = item.imageUrl,
                        title = item.title,
                        query = viewModel.queryText,
                        onClick = {
                            navigator.navigate(
                                DetailScreenDestination(
                                    imageUrl = item.imageUrl,
                                    title = item.title,
                                    author = item.author ?: "Author N/A",
                                    description = item.description ?: "Description N/A",
                                    imageWidth = item.imageWidth ?: "-1",
                                    imageHeight = item.imageHeight ?: "-1"
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

