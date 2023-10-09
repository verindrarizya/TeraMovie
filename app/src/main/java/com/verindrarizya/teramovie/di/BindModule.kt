package com.verindrarizya.teramovie.di

import com.verindrarizya.teramovie.data.repository.MovieRepository
import com.verindrarizya.teramovie.data.repository.MovieRepositoryImpl
import com.verindrarizya.teramovie.data.source.local.MovieLocalDataSource
import com.verindrarizya.teramovie.data.source.local.MovieLocalDataSourceImpl
import com.verindrarizya.teramovie.data.source.remote.MovieRemoteDataSource
import com.verindrarizya.teramovie.data.source.remote.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindMovieLocalDataSource(movieLocalDataSourceImpl: MovieLocalDataSourceImpl): MovieLocalDataSource

    @Binds
    abstract fun bindMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

}