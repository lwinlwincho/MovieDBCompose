package com.lwinlwincho.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * from movieEntity WHERE id = :id ")
    fun getMovieById(id: Long): Flow<MovieEntity?>

    @Query("SELECT * from movieEntity order by id ASC ")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Delete
    suspend fun delete(movie: MovieEntity)

}