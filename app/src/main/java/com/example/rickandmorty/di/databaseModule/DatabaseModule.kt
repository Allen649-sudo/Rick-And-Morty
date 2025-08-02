package com.example.rickandmorty.di.databaseModule

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.data.local.AppDatabase
import com.example.rickandmorty.data.local.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty_db"
        ).build()

    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.characterDao()
}
