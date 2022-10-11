package com.example.running_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunningDao{

}

@Dao
interface UserDao {
    @Query("select * from user")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    //Delete is not an option in our app
//    @Delete
//    suspend fun delete(user: User)
}