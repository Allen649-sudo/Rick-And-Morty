package com.example.rickandmorty.di.repositoryModule

import com.example.rickandmorty.data.repository.CharactersRepositoryImpl
import com.example.rickandmorty.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharactersRepository(
        impl: CharactersRepositoryImpl
    ): CharactersRepository
}
