package com.example.rickandmorty

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.dataClasses.CharactersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository) : ViewModel() {

    private val _characters = MutableStateFlow<List<CharactersResponse>>(emptyList())
    val  characters: StateFlow<List<CharactersResponse>> get() = _characters

    private val _currentCharacter = MutableStateFlow<CharactersResponse?>(null)
    val currentCharacter: StateFlow<CharactersResponse?> get() = _currentCharacter

    // текст с ошибкой
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingPage = false

    init {
        fetchCharacters()
    }

    fun fetchCharacters(status: String? = "alive", errorText: String = "ERROR 404") {
        viewModelScope.launch {
            _isLoading.value = true // 👉 запуск загрузки
            try {
                _errorMessage.value = null
                val characters = repository.fetchCharacters()
                _characters.value = if (status != null) {
                    characters.filter { it.status.equals(status, ignoreCase = true) }
                } else {
                    characters
                }
            } catch (e: Exception) {
                _characters.value = emptyList()
                _errorMessage.value = errorText
            } finally {
                _isLoading.value = false // 👉 завершение загрузки
            }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun refreshCharacters(errorText: String) {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchCharacters(status = null, errorText = errorText)
            _isRefreshing.value = false
        }
    }


    fun showDetailedInfo(characterId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getDetailedInfoCharacter(characterId)
                _currentCharacter.value = result
            } catch (e: Exception) {
                Log.e("CharactersViewModel", "Ошибка при получении данных: ${e.message}", e)
            }
        }
    }

    fun loadNextPage() {
        if (isLoadingPage || isLastPage) return

        isLoadingPage = true
        viewModelScope.launch {
            try {
                val response = repository.getCharactersPage(currentPage)
                if (response.isNotEmpty()) {
                    val updated = _characters.value.toMutableList().apply {
                        addAll(response)
                    }
                    _characters.value = updated
                    currentPage++
                } else {
                    isLastPage = true
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки"
            } finally {
                isLoadingPage = false
            }
        }
    }
}