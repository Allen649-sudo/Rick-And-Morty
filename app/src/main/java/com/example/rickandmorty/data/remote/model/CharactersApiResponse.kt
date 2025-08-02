package com.example.rickandmorty.data.remote.model

data class CharactersApiResponse(
    val info: Info,
    val results: List<CharactersResponse>
)
