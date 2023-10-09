package com.verindrarizya.teramovie.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.verindrarizya.teramovie.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insertAll(vararg movies: MovieEntity)

    @Query("DELETE FROM movie")
    suspend fun cleanMoviesData()

}