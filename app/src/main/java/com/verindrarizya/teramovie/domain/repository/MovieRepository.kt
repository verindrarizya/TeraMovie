package com.verindrarizya.teramovie.domain.repository

import com.verindrarizya.teramovie.domain.entity.Movie
import com.verindrarizya.teramovie.util.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<List<Movie>>

    suspend fun fetchMovies(): Result<String>

}