package com.jakey.aetnacompose.presentation.screens

sealed class Screen(val route: String) {
    object SearchScreen : Screen(
        route = "search_screen/{imageUrl}/{title}/{description}/" +
                "{imageWidth}/{imageHeight}/{imageUrl}/{author}"
    )
    object DetailScreen : Screen(route = "detail_screen")
}
