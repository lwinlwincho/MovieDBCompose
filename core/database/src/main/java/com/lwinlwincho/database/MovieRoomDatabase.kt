package com.lwinlwincho.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieRoomDatabase::class.java, "movie_database")
                    .build()
                    .also { Instance = it }
            }

        }
    }
}