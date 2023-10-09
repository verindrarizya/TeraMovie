package com.verindrarizya.teramovie.data.source.remote

import com.verindrarizya.teramovie.data.source.remote.api.ApiResponse
import com.verindrarizya.teramovie.data.source.remote.api.ApiService
import com.verindrarizya.teramovie.data.source.remote.response.MovieItem
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRemoteDataSource {
    override suspend fun getMovie(): ApiResponse<List<MovieItem>> {
        return try {
            // i didn't switch dispatchers because implementation of suspend function in Retrofit
            // instance has it's own dispatcher and it doesn't run in main thread, so manually
            // switching dispatchers would be redundant
            val movieResponse = apiService.getMovies()
            ApiResponse.Success(movieResponse.results)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }
}