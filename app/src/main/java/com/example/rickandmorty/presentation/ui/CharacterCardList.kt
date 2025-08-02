package com.example.rickandmorty.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.presentation.viewmodel.CharactersViewModel
import com.example.rickandmorty.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun CharacterCardList(
    viewModel: CharactersViewModel,
    onShowDetailInfo: (Int) -> Unit,
) {
    val characters by viewModel.characters.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    val searchQuery by viewModel.searchQuery.collectAsState()

    val context = LocalContext.current
    val errorText = context.getString(R.string.ERROR_404)
    val errorMessage by viewModel.errorMessage.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val listState = rememberLazyGridState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                if (index != null && index >= characters.size - 3) {
                    viewModel.loadNextPage()
                }
            }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Поиск по имени") },
                modifier = Modifier
                    .weight(3f)
                    .padding(end = 8.dp),
                singleLine = true
            )

            Column(modifier = Modifier.weight(1f)) {
                Button(onClick = { expanded = true }) {
                    Text(text = selectedFilter ?: "Фильтр")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val filters = listOf("Alive", "Dead", "unknown", "all characters")
                    filters.forEach { filter ->
                        DropdownMenuItem(onClick = {
                            selectedFilter = if (filter == "all characters") null else filter
                            viewModel.fetchCharacters(selectedFilter, errorText)
                            expanded = false
                        }) {
                            Text(text = filter)
                        }
                    }
                }
            }
        }

        // 💡 Pull-to-Refresh
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refreshCharacters(errorText) }
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Сетка карточек
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    val filtered = characters.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }
                    items(filtered) { character ->
                        CharacterCard(viewModel, character, onShowDetailInfo)
                    }
                }
            }
        }

        if (errorMessage != null) {
            androidx.compose.material.Text(
                text = errorMessage ?: "",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
