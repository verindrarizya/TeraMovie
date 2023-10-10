package com.verindrarizya.teramovie.presentation

import com.verindrarizya.teramovie.domain.entity.Movie

data class MovieUiState(
    val isLoading: Boolean = true,
    val movieList: List<Movie> = listOf()
)