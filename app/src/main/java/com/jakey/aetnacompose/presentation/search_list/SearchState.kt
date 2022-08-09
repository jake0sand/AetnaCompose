package com.jakey.aetnacompose.presentation.search_list

import com.jakey.aetnacompose.domain.list.DetailItem

data class SearchState(
    val searchResults: List<DetailItem>? = null,
    val historyList: List<String>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null
)
