package com.jakey.aetnacompose.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jakey.aetnacompose.R
import com.jakey.aetnacompose.presentation.SearchState

@Composable
fun Loader(
    state: SearchState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading == true) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.lf30_editor_wpwax4bw)
        )
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        // Had a fun lottie animation, but then realize that you asked for UI to not be blocked.
//        LottieAnimation(
//            contentScale = ContentScale.FillWidth,
//            composition = composition,
//            speed = 2f,
//            modifier = modifier
//        )
    }
}