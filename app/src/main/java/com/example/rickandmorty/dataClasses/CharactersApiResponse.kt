package com.example.rickandmorty.dataClasses

data class CharactersApiResponse(
    val info: Info,
    val results: List<CharactersResponse>
)
