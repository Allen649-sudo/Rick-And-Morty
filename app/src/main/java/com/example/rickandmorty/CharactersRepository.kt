package com.example.rickandmorty

import com.example.rickandmorty.dataClasses.CharacterEntity
import com.example.rickandmorty.dataClasses.CharactersApiResponse
import com.example.rickandmorty.dataClasses.CharactersResponse
import com.example.rickandmorty.dataClasses.Location
import com.example.rickandmorty.dataClasses.Origin
import com.google.gson.Gson
import jakarta.inject.Inject

class CharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val dao: CharacterDao
) {
    suspend fun fetchCharacters(): List<CharactersResponse> {
        return try {
            val response = rickAndMortyApi.getCharacters(page = 1).results // ← добавлено page = 1
            val entities = response.map {
                CharacterEntity(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    species = it.species,
                    type = it.type,
                    gender = it.gender,
                    originName = it.origin.name,
                    locationName = it.location.name,
                    image = it.image,
                    url = it.url,
                    created = it.created
                )
            }
            dao.clearCharacters()
            dao.insertCharacters(entities)
            response
        } catch (e: Exception) {
            // Если ошибка — читаем данные из базы
            dao.getAllCharacters().map {
                CharactersResponse(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    species = it.species,
                    type = it.type,
                    gender = it.gender,
                    origin = Origin(it.originName, ""),
                    location = Location(it.locationName, ""),
                    image = it.image,
                    episode = emptyList(),
                    url = it.url,
                    created = it.created
                )
            }
        }
    }

    suspend fun getDetailedInfoCharacter(id: Int): CharactersResponse {
        return rickAndMortyApi.getDetailedInfoCharacter(id)
    }

    suspend fun getCharactersPage(page: Int): List<CharactersResponse> {
        return try {
            rickAndMortyApi.getCharacters(page).results
        } catch (e: Exception) {
            emptyList() // или из базы если offline
        }
    }
}