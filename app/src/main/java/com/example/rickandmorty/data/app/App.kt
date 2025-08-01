package com.example.rickandmorty.data.app

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }
}
