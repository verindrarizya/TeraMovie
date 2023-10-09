package com.verindrarizya.teramovie.data.repository

import com.verindrarizya.teramovie.data.model.Movie
import com.verindrarizya.teramovie.util.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<List<Movie>>

    suspend fun fetchMovies(): Result<String>

}