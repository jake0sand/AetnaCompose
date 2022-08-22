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

    /**
     * I've returned the history as a list instead of returning a string then converting to a list.
     * I was doing an extra completely unnecessary step before, and also it did not work right when a
     * comma-separated query was entered as it would .split(",") making each word (in the c-separated query)
     * save as a separate entry in the history.
     */
    var history by mutableStateOf(listOf(""))
    var dataStoreIsEmpty by mutableStateOf(false)
    var state by mutableStateOf(SearchState())
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            // I can non-null assert here (I think), because Data Store requires a default value.
            history = dataStore.readAllValues()?.map { it.toString() }!!
            getHistory()
        }
    }

    var queryText by mutableStateOf("")

    private fun getHistory() {
        viewModelScope.launch{
            dataStoreIsEmpty = dataStore.readAllValues()?.isEmpty() == true
        }
    }

    fun onSearch(query: String) {
        queryText = query
        searchJob?.cancel()

        // misread instructions and did search ahead instead of search on button click.
        // took way longer, but learned a ton along the way. WORTH IT.

        // Biggest problem was I wasn't able to figure out how to not save every character to
        // Data Store.. I'm sure it's something obvious, I just could not reach the solution.

        if (queryText.isNotBlank()) {
            searchJob = viewModelScope.launch {
                // delay here to allow user a bit of time to type a query before searching
                delay(1000L)
                repository.getImages(query).onEach { result ->

                    state = state.copy(error = null)

                    state = state.copy(isLoading = true)
                    when (result) {

                        is Resource.Success -> {
                            state = state.copy(
                                searchResults = result.data,
                                isLoading = false
                            )
                            dataStore.save(queryText, queryText)
                            history = dataStore.readAllValues()?.map { it.toString() }!!
                            dataStoreIsEmpty = false
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

}
