package com.jakey.aetnacompose.presentation.search_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jakey.aetnacompose.data.data_store.DataStoreManager
import com.jakey.aetnacompose.presentation.composables.Loader
import com.jakey.aetnacompose.presentation.composables.SearchItem
import com.jakey.aetnacompose.presentation.destinations.DetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Destination(start = true)
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
) {
    val scope = rememberCoroutineScope()
    val viewModel: ImageListViewModel = hiltViewModel()
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    var queryText by rememberSaveable {
        mutableStateOf(viewModel.queryText)
    }

    LaunchedEffect(true) {
        viewModel.history = dataStore.readAllValues().toString()
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
        Column {
            OutlinedTextField(
                value = queryText,
                onValueChange = {
                    queryText = it
                    viewModel.onSearch(queryText)
                    scope.launch {
                        dataStore.save(queryText, queryText)
                    }
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

                    IconButton(onClick = {
                        queryText = ""
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear search field"
                        )


                    }
                },
                maxLines = 1,
                singleLine = true
            )

            Button(
                onClick = { scope.launch {
                    dataStore.deleteAllKeys()
                    viewModel.history = ""
                } },
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Delete History")

            }

            if (state.isLoading == true) {
                Loader(
                    state = state,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (state.error != null) {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = state.error,
                    )
                }
                LocalSoftwareKeyboardController.current?.hide()
            }

            AnimatedVisibility(
                visible = queryText.isBlank()

            ) {
                Column() {
                    var dataStoreIsEmpty = true
                    LaunchedEffect(true) {
                        dataStoreIsEmpty = dataStore.readAllValues().isNullOrEmpty()
                    }
                    val historyList = viewModel.history
                        .removeSurrounding("[", "]")
                        .split(",")
                        .map(String::trim)

                    Text(
                        text = if (dataStoreIsEmpty) "No recent searches." else "Recent Searches",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyColumn(Modifier.padding(horizontal = 16.dp)) {

                        historyList.toMutableStateList()
                        items(historyList.size) { index ->
                            Text(
                                text = historyList[index],
                                modifier = Modifier
                                    .clickable {
                                        queryText = historyList[index]
                                        viewModel.onSearch(historyList[index])
                                    }
                                    .background(
                                        color =
                                        if (index % 2 == 0) Color.White
                                        else Color.LightGray
                                    )
                                    .fillParentMaxWidth()
                                    ,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            }

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
                                query = queryText,
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
    }
}
