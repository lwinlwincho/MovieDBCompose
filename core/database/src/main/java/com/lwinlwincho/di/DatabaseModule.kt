package com.lwinlwincho.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn
object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRoomDatabase(@ApplicationContext context: Context): com.lwinlwincho.database.MovieRoomDatabase {
        return Room.databaseBuilder(
            context,
            com.lwinlwincho.database.MovieRoomDatabase::class.java,
            "movie_database"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @ViewModelScoped
    fun provideMovieDao(movieRoomDatabase: com.lwinlwincho.database.MovieRoomDatabase): com.lwinlwincho.database.MovieDao {
        return movieRoomDatabase.movieDao()
    }
}