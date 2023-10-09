package com.verindrarizya.teramovie.presentation

import com.verindrarizya.teramovie.data.model.Movie

data class MovieUiState(
    val isLoading: Boolean = true,
    val movieList: List<Movie> = listOf()
)