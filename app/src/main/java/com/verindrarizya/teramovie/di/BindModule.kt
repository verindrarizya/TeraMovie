package com.verindrarizya.teramovie.di

import com.verindrarizya.teramovie.data.MovieRepositoryImpl
import com.verindrarizya.teramovie.data.source.local.MovieLocalDataSource
import com.verindrarizya.teramovie.data.source.local.MovieLocalDataSourceImpl
import com.verindrarizya.teramovie.data.source.remote.MovieRemoteDataSource
import com.verindrarizya.teramovie.data.source.remote.MovieRemoteDataSourceImpl
import com.verindrarizya.teramovie.domain.repository.MovieRepository
import com.verindrarizya.teramovie.domain.usecase.MovieUseCase
import com.verindrarizya.teramovie.domain.usecase.MovieUseCaseImpl
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

    @Binds
    abstract fun bindMovieUseCase(movieUseCaseImpl: MovieUseCaseImpl): MovieUseCase
}