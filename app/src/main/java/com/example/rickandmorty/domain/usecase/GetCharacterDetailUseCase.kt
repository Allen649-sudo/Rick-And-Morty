package com.example.rickandmorty.domain.usecase

import com.example.rickandmorty.domain.repository.CharactersRepository
import jakarta.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharactersRepository
) {
    suspend operator fun invoke(id: Int) = repository.getDetailedInfoCharacter(id)
}
