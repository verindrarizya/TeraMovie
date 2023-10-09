package com.verindrarizya.teramovie.data.source.local

import com.verindrarizya.teramovie.data.source.local.entity.MovieEntity
import com.verindrarizya.teramovie.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {
    override fun getMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    override suspend fun insertAllMovies(movieEntities: List<MovieEntity>) {
        movieDao.cleanMoviesData()
        movieDao.insertAll(*movieEntities.toTypedArray())
    }
}