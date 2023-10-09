package com.verindrarizya.teramovie.data.repository

import com.verindrarizya.teramovie.data.model.Movie
import com.verindrarizya.teramovie.data.source.local.MovieLocalDataSource
import com.verindrarizya.teramovie.data.source.remote.MovieRemoteDataSource
import com.verindrarizya.teramovie.data.source.remote.api.ApiResponse
import com.verindrarizya.teramovie.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override fun getMovies(): Flow<List<Movie>> {
        return movieLocalDataSource.getMovies()
            .map { it.map { movieEntity -> movieEntity.mapToModel() } }
    }

    override suspend fun fetchMovies(): Result<String> {

        return when (val remoteResponse = movieRemoteDataSource.getMovie()) {
            is ApiResponse.Error -> Result.Failed(remoteResponse.exception)
            is ApiResponse.Success -> {
                val movieEntities = remoteResponse.data.map { it.toMovieEntity() }
                movieLocalDataSource.insertAllMovies(movieEntities)
                Result.Success("Movies fetched successfully")
            }
        }
    }
}