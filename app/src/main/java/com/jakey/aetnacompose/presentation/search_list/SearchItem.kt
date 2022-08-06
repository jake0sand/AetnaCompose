package com.jakey.aetnacompose.presentation.search_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
//            AsyncImage(
//                contentScale = ContentScale.FillBounds,
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(image)
//                    .build(),
//                contentDescription = "Image from search query $query",
//                modifier = Modifier.fillMaxSize()
//            )
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