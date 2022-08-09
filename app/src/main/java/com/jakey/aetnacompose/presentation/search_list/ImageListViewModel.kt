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
    val dataStore: DataStoreManager
) : ViewModel() {


    var history: String = ""
    var state by mutableStateOf(SearchState())
    private var searchJob: Job? = null

    var isHistoryVisible = true

    var query: String = ""

    var dataStoreList = mutableListOf<String>("")

    init {
        viewModelScope.launch {
            history = dataStore.readAllValues().toString()
        }
    }

    fun onSearch(query: String) {

        searchJob?.cancel()

        // Talking point, misread instructions and search ahead instead of button click search.
        if (query.isNotBlank()) {
            searchJob = repository.getImages(query).onEach() { result ->
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
                        history = query
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {}
                }

            }.launchIn(viewModelScope)
        }
    }
}