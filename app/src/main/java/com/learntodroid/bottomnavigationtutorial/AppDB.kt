package com.learntodroid.bottomnavigationtutorial

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Episode::class,Location::class, PageKey::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class AppDB : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun pageKeyDao(): PageKeyDao
    abstract fun episodeDao(): EpisodeDao

    companion object {
        @Volatile private var instance: AppDB? = null

        fun getDatabase(context: Context): AppDB =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDB::class.java, "pagination")
                .fallbackToDestructiveMigration()
                .build()
    }
}