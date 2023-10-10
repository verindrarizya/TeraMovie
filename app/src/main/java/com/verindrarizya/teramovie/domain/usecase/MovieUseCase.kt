package com.verindrarizya.teramovie.domain.usecase

import com.verindrarizya.teramovie.domain.entity.Movie
import com.verindrarizya.teramovie.util.Result
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovies(): Flow<List<Movie>>

    suspend fun fetchMovies(): Result<String>

}