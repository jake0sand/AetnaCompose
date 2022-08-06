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

    fun onSearch(query: String) {

            repository.getImages(query).onEach() { result ->
                viewModelScope.launch {
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
                }
            }.launchIn(viewModelScope)

    }
}