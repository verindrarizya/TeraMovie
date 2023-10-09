package com.verindrarizya.teramovie.data.source.remote

import com.verindrarizya.teramovie.data.source.remote.api.ApiResponse
import com.verindrarizya.teramovie.data.source.remote.response.MovieItem

interface MovieRemoteDataSource {
    suspend fun getMovie(): ApiResponse<List<MovieItem>>
}