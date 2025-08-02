package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.data.remote.model.CharactersResponse
import com.example.rickandmorty.domain.model.Character

interface CharactersRepository {
    suspend fun getCharacters(): List<CharactersResponse>
    suspend fun getDetailedInfoCharacter(id: Int): CharactersResponse
    suspend fun getCharactersPage(page: Int): List<CharactersResponse>
}
