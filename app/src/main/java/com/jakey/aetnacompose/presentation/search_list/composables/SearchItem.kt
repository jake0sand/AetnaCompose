package com.jakey.aetnacompose.presentation.search_list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SearchItem(
    image: String,
    title: String,
    query: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column() {
            GlideImage(
                contentScale = ContentScale.Crop,
                imageModel = image,
                contentDescription = "Image from search query $query",
                modifier = Modifier.fillMaxSize()
            )
            Text(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(8.dp),
                text = title,
                maxLines = 2,
                style = MaterialTheme.typography.h6
            )
        }
    }
}