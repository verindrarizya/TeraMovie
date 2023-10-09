package com.verindrarizya.teramovie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.teramovie.data.repository.MovieRepository
import com.verindrarizya.teramovie.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieUiState: MutableStateFlow<MovieUiState> = MutableStateFlow(MovieUiState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    init {
        fetchMovies()
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            movieRepository.getMovies().collect { movieList ->
                _movieUiState.update {
                    it.copy(movieList = movieList)
                }
            }
        }
    }

    fun fetchMovies() {
        _movieUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = movieRepository.fetchMovies()
            when (result) {
                is Result.Failed -> {
                    _movieUiState.update { it.copy(isLoading = false) }
                    _message.emit(result.exception.message ?: "Something went wrong")
                }

                is Result.Success -> {
                    _movieUiState.update { it.copy(isLoading = false) }
                    _message.emit(result.data)
                }
            }
        }
    }
}