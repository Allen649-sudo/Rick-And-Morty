package com.example.rickandmorty

import com.example.rickandmorty.dataClasses.CharactersApiResponse
import com.example.rickandmorty.dataClasses.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersApiResponse

    @GET("character/{id}")
    suspend fun getDetailedInfoCharacter(
        @Path("id") id: Int
    ): CharactersResponse
}
