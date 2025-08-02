package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.CharactersRepository
import jakarta.inject.Inject

class GetCharactersPage @Inject constructor(private val repository: CharactersRepository) {
    suspend operator fun invoke(page: Int) = repository.getCharactersPage(page)
}