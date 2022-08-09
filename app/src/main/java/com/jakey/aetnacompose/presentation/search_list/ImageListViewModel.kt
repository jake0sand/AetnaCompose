package com.jakey.aetnacompose.presentation.search_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakey.aetnacompose.data.data_store.DataStoreManager
import com.jakey.aetnacompose.data.repository.ImageListRepository
import com.jakey.aetnacompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageListRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    var history by mutableStateOf("")
    var state by mutableStateOf(SearchState())
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            history = dataStore.readAllValues().toString()
        }
    }

    var queryText by mutableStateOf("")
    fun onSearch(query: String) {
        queryText = query
        searchJob?.cancel()

        // misread instructions and did search ahead instead of search on button click.
        // took way longer, but learned a ton along the way. WORTH IT.

        // Biggest problem was I wasn't able to figure out how to not save every character to
        // Data Store.. I'm sure it's something obvious, I just could not reach the solution.
        if (queryText.isNotBlank()) {

            searchJob = repository.getImages(queryText).onEach() { result ->
                state = state.copy(error = null)
                // delay here to allow user a bit of time to type a query before searching
                delay(500L)

                state = state.copy(isLoading = true)
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            searchResults = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {}
                }
                history = dataStore.readAllValues().toString()

            }.launchIn(viewModelScope)
        }
    }
}
