package com.example.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.local.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
