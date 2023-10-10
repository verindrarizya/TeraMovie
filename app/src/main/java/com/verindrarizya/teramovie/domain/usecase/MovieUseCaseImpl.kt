package com.verindrarizya.teramovie.domain.usecase

import com.verindrarizya.teramovie.domain.entity.Movie
import com.verindrarizya.teramovie.domain.repository.MovieRepository
import com.verindrarizya.teramovie.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override fun getMovies(): Flow<List<Movie>> {
        return movieRepository.getMovies()
    }

    override suspend fun fetchMovies(): Result<String> {
        return movieRepository.fetchMovies()
    }


}