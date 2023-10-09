package com.verindrarizya.teramovie.data.source.local

import com.verindrarizya.teramovie.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun getMovies(): Flow<List<MovieEntity>>

    suspend fun insertAllMovies(movieEntities: List<MovieEntity>)

}