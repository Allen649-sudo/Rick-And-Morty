package com.example.rickandmorty

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(
        api: RickAndMortyApi,
        dao: CharacterDao
    ): CharactersRepository {
        return CharactersRepository(api, dao)
    }
}
