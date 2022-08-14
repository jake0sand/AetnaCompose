package com.jakey.aetnacompose.presentation.search_list.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jakey.aetnacompose.presentation.search_list.ImageListViewModel

@Composable
fun OutlinedSearchField(viewModel: ImageListViewModel) {
    OutlinedTextField(
        value = viewModel.queryText,
        onValueChange = {
            viewModel.queryText = it
            viewModel.onSearch(viewModel.queryText)
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
                viewModel.queryText = ""
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
}