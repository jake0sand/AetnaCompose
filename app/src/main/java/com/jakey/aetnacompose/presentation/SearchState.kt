package com.jakey.aetnacompose.presentation

import com.jakey.aetnacompose.domain.image.ListItem

data class SearchState(
    val searchResults: List<ListItem>? = null,
    val historyList: List<String>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null
)
