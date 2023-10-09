package com.verindrarizya.teramovie.data.source.remote.api

import com.verindrarizya.teramovie.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = ApiConstant.API_KEY
    ): MovieResponse

}