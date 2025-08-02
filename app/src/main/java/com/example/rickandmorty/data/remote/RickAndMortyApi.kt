package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.remote.model.CharactersResponse
import com.example.rickandmorty.data.remote.model.CharactersApiResponse
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
