package com.lwinlwincho.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lwinlwincho.database.MovieDao
import com.lwinlwincho.database.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieRoomDatabase(@ApplicationContext context: Context): MovieRoomDatabase {
        return MovieRoomDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieRoomDatabase: MovieRoomDatabase): MovieDao {
        return movieRoomDatabase.movieDao()
    }
}