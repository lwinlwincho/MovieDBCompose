package com.lwinlwincho.di

import android.content.Context
import com.lwinlwincho.roomDatabase.MovieDao
import com.lwinlwincho.roomDatabase.MovieRoomDatabase
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