package com.jakey.aetnacompose.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailScreen() {
    //todo: figure out details screen
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        GlideImage(modifier = Modifier.fillMaxWidth(), imageModel = "https://live.staticflickr.com/65535/52267147101_66cab505af_m.jpg",
        contentScale = ContentScale.Crop)
        Text("Hello", fontSize = 36.sp)
        Text("Hello", fontSize = 36.sp)
        Text("Hello", fontSize = 36.sp)
        Text("Hello", fontSize = 36.sp)
        Text("Hello", fontSize = 36.sp)
    }
}