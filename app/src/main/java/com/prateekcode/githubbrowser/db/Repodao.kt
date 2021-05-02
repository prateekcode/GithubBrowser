package com.prateekcode.githubbrowser.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Repodao {

    //For Querying All the Data
    @Query("SELECT * FROM RepoTable")
    fun getAllRepo():List<Repotity>

    //For Inserting the single repo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repotity: Repotity)

    //For deleting the entire db
    @Query("DELETE FROM RepoTable")
    suspend fun deleteAll()

    //For deleting the single repo
    @Delete
    suspend fun delete(repotity: Repotity)

}