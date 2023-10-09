package com.verindrarizya.teramovie.di

import android.content.Context
import androidx.room.Room
import com.verindrarizya.teramovie.data.source.local.room.MovieDao
import com.verindrarizya.teramovie.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(
        movieDatabase: MovieDatabase
    ): MovieDao = movieDatabase.movieDao()
}