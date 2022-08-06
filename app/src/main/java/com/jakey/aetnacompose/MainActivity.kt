package com.jakey.aetnacompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.jakey.aetnacompose.data.remote.FlickrApi
import com.jakey.aetnacompose.presentation.ImageListViewModel
import com.jakey.aetnacompose.presentation.search_list.SearchItem
import com.jakey.aetnacompose.ui.theme.AetnaComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            val viewModel: ImageListViewModel = hiltViewModel()

            AetnaComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        var text by rememberSaveable {
                            mutableStateOf("")
                        }
                        val state = viewModel.state
                        TextField(value = text, onValueChange = {
                            text = it
                            viewModel.onSearch(text)

                        })

                        if (state.isLoading == true) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator()
                            }
                        }

                        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                            state.searchResults?.let {
                                it.forEach { item ->
                                    item {
                                        SearchItem(
                                            image = item.imageUrl,
                                            title = item.title,
                                            query = text,
                                            onClick = {
                                                Toast.makeText(this@MainActivity, item.imageUrl , Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        if (state.error != null) {
                            Snackbar(modifier = Modifier.padding(8.dp)) {
                                Text(text = state.error)
                            }
                        }
                    }
                }
            }
        }
    }
}

