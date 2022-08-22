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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jakey.aetnacompose.presentation.search_list.ImageListViewModel

@Composable
fun ColumnScope.HistoryLazyColumn(
    viewModel: ImageListViewModel,
    isHistoryEmpty: Boolean,
) {

    AnimatedVisibility(
        visible = viewModel.queryText.isBlank()

    ) {
        Column {
            Text(
                text = if (!isHistoryEmpty) {
                    "Recent Searches"
                } else {
                    "No recent searches."
                },
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(Modifier.padding(horizontal = 16.dp)) {

                val history = viewModel.history.reversed()

                items(history.size) { index ->

                    Text(
                        text = history[index],
                        modifier = Modifier
                            .clickable {
                                viewModel.queryText = history[index]
                                viewModel.onSearch(history[index])
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

