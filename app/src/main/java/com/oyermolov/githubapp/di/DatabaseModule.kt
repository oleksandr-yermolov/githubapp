package com.oyermolov.githubapp.di

import android.content.Context
import androidx.room.Room
import com.oyermolov.githubapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}