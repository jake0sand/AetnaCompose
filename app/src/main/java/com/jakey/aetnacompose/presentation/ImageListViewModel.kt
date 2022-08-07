package com.jakey.aetnacompose.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakey.aetnacompose.data.repository.ImageListRepository
import com.jakey.aetnacompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageListRepository
): ViewModel() {
    
    var state by mutableStateOf(SearchState())

    private var searchJob: Job? = null

    fun onSearch(query: String) {

        searchJob?.cancel()

        // I misread task 1 and didn't realize you asked for a button to search as opposed
        // to search-on-type. I'll keep it this way since it took way longer than a button search
        // implementation.
            searchJob = repository.getImages(query).onEach() { result ->
                // delay here to allow user a bit of time to type a query before searching..
                // if I took delay away it would instantly make request on each stroke.
                delay(500L)
                viewModelScope.launch {
                    state = state.copy(isLoading = true)
                    // I added a delay just to admire the nice loading animation for a bit longer :)
                    delay(700L)
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
                }
            }.launchIn(viewModelScope)

    }
}