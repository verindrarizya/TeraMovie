package com.verindrarizya.teramovie.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.verindrarizya.teramovie.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}