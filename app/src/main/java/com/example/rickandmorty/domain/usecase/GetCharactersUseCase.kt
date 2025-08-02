package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.CharactersRepository
import jakarta.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharactersRepository
) {
    suspend operator fun invoke() = repository.getCharacters()
}