package com.jakey.aetnacompose.presentation.search_list.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jakey.aetnacompose.data.data_store.DataStoreManager
import com.jakey.aetnacompose.presentation.search_list.ImageListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ColumnScope.HistoryLazyColumn(
    viewModel: ImageListViewModel,
    dataStore: DataStoreManager,
    scope: CoroutineScope
) {
    AnimatedVisibility(
        visible = viewModel.queryText.isBlank()

    ) {
        Column() {
            var dataStoreIsEmpty by rememberSaveable { mutableStateOf(true) }
            LaunchedEffect(true) {
                dataStoreIsEmpty = dataStore.readAllValues().isNullOrEmpty()
            }



            Text(
                text = if (dataStoreIsEmpty) "No recent searches." else "Recent Searches",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                scope.launch { dataStore.readAllValues().toString() }

                val historyList = viewModel.history
                .removeSurrounding("[", "]")
                .split(",")
                .map(String::trim).reversed()
                historyList.toMutableStateList()
                items(historyList.size) { index ->

                    Text(
                        text = historyList[index],
                        modifier = Modifier
                            .clickable {
                                viewModel.queryText = historyList[index]
                                viewModel.onSearch(historyList[index])
                            }
                            .background(
                                color =
                                if (index % 2 == 0) Color.White
                                else Color.LightGray
                            )
                            .fillParentMaxWidth(),
                        style = MaterialTheme.typography.h6
                    )
                }

            }
        }
    }
}
