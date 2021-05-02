package com.prateekcode.githubbrowser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Repotity::class], version = 2, exportSchema = false)
abstract class RepoDatabase:RoomDatabase() {

    abstract fun repoDao(): Repodao

    companion object{
        @Volatile
        private var INSTANCE: RepoDatabase?= null

        fun getDatabase(context: Context): RepoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RepoDatabase::class.java, "repo_database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }


}