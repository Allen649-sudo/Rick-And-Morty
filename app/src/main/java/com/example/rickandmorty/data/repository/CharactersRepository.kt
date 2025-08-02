package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.RickAndMortyApi
import com.example.rickandmorty.data.local.model.CharacterEntity
import com.example.rickandmorty.data.remote.model.CharactersResponse
import com.example.rickandmorty.data.remote.model.Location
import com.example.rickandmorty.data.remote.model.Origin
import com.example.rickandmorty.domain.repository.CharactersRepository
import jakarta.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val dao: CharacterDao
) : CharactersRepository {

    override suspend fun getCharacters(): List<CharactersResponse> {
        return try {
            val response = rickAndMortyApi.getCharacters(page = 1).results
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

    override suspend fun getDetailedInfoCharacter(id: Int): CharactersResponse {
        return rickAndMortyApi.getDetailedInfoCharacter(id)
    }

    override suspend fun getCharactersPage(page: Int): List<CharactersResponse> {
        return try {
            rickAndMortyApi.getCharacters(page).results
        } catch (e: Exception) {
            emptyList()
        }
    }
}
