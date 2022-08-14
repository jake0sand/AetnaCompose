package com.jakey.aetnacompose.presentation.search_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jakey.aetnacompose.data.data_store.DataStoreManager
import com.jakey.aetnacompose.presentation.search_list.composables.HistoryLazyColumn
import com.jakey.aetnacompose.presentation.search_list.composables.Loader
import com.jakey.aetnacompose.presentation.search_list.composables.OutlinedSearchField
import com.jakey.aetnacompose.presentation.search_list.composables.SearchLazyColumn
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
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

            if (state.error != null) {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = state.error,
                    )
                }
                LocalSoftwareKeyboardController.current?.hide()
            }

            OutlinedSearchField(viewModel = viewModel)

            if (state.isLoading == true) {
                Loader(
                    state = state,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            DeleteButton(
                scope = scope,
                dataStore = dataStore,
                viewModel = viewModel,
                modifier = Modifier.Companion
                    .align(alignment = Alignment.End)
                    .padding(horizontal = 16.dp)
            )

            HistoryLazyColumn(
                viewModel = viewModel,
                dataStore = dataStore,
                scope = scope
            )

            SearchLazyColumn(
                state = state,
                viewModel = viewModel,
                navigator = navigator
            )
        }
    }
}



@Composable
private fun DeleteButton(
    scope: CoroutineScope,
    dataStore: DataStoreManager,
    viewModel: ImageListViewModel,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            scope.launch {
                dataStore.deleteAllKeys()
                viewModel.history = ""
            }
        },
        modifier = modifier
    ) {
        Text(text = "Delete History")

    }
}

