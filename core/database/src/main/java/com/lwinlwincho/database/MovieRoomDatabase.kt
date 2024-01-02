package com.lwinlwincho.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile private var instance: MovieRoomDatabase? = null


        fun getInstance(context: Context): MovieRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MovieRoomDatabase {
            return Room.databaseBuilder(context, MovieRoomDatabase::class.java, "movie_database")
                .build()
        }
    }
}