package com.example.running_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunningDao {

    // ***********************************Functions***********************************
    // Get all the running records
    @Query("SELECT * FROM runningdb")
    fun getAll(): LiveData<List<RunningDB>>

    // ***********************************CRUD***********************************

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(runningDB: RunningDB): Long

    @Delete
    suspend fun delete(runningDB: RunningDB)

    @Query("DELETE FROM runningdb")
    fun deleteAll()


}

@Dao
interface UserDao{

}