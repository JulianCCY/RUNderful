package com.example.running_app.data.db

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunningDao{

    // Add new running record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewRecord(running: Running): Long

    // Get the rid for adding coordinate
    @Query("SELECT rid FROM running ORDER BY rid DESC LIMIT 1")
    fun getKeyForCoordinate(): Long

    // Getting all running records
    @Query("SELECT * FROM running ORDER BY rid DESC")
    fun getAllRecords(): LiveData<List<Running>>

    // get record by rid
    @Query("SELECT * FROM running WHERE rid = :id")
    fun getRecordByRID(id: Long): LiveData<Running>

    // get latest record
    @Query("SELECT * FROM running ORDER BY rid DESC LIMIT 1")
    fun getLatestRecord(): LiveData<Running>

    // get latest step
    @Query("SELECT totalStep FROM running ORDER BY rid DESC LIMIT 1")
    fun getLatestSteps(): Int

    // get average steps
    @Query("SELECT ROUND(AVG(totalStep)) FROM running")
    fun getAverageSteps(): Int

    // get latest distance
    @Query("SELECT distance FROM running ORDER BY rid DESC LIMIT 1")
    fun getLatestDistance(): Double

    // get average distance
    @Query("SELECT ROUND(AVG(distance), 2) from running")
    fun getAverageDistance(): Double

    // get latest speed
    @Query("SELECT avgSpeed FROM running ORDER BY rid DESC LIMIT 1")
    fun getLatestSpeed(): Double

    // get average speed
    @Query("SELECT ROUND(AVG(avgSpeed), 2) from running")
    fun getAverageSpeed(): Double

    // get latest and second last to compare
    @Query("SELECT * FROM running ORDER BY rid DESC LIMIT 2")
    fun getLatestAndPreviousRunningRecord(): LiveData<List<Running>>

    // delete record by rid
    @Query("DELETE FROM running WHERE rid = :id")
    fun deleteRecordByRID(id: Long)

    // delete all record
    @Query("DELETE FROM running")
    fun deleteAllRecords()

    //********************************** StatOverViewForUI **********************************

    // number of running records
    @Query("SELECT COUNT(*) FROM running")
    fun getNoOfRecords(): Int

    // total steps
    @Query("SELECT SUM(totalStep) FROM running")
    fun getTotalSteps(): Int

    // total distance user runs
    @Query("SELECT SUM(distance) FROM running")
    fun getTotalDistance(): Double

    // Average speed of last five records
    @Query("SELECT avgSpeed FROM running ORDER BY rid DESC LIMIT 5")
    fun getLastFiveAverageSpeed(): LiveData<List<Double>>

    // Average heart rate of last five records
    @Query("SELECT avgHR FROM running WHERE avgHR <> 0 ORDER BY rid LIMIT 5")
    fun getLastFiveAverageHeartRate(): LiveData<List<Int>>
}

@Dao
interface CoordinatesDao{

    // useless
    @Query("SELECT * FROM coordinates")
    fun getALLCoordinates(): LiveData<List<Coordinates>>

    // get all related coordinate for a running record
    @Query("select * from coordinates join running on coordinates.runningId = running.rid where running.rid = :running_id")
    fun getAllRelatedCoordinates(running_id: Long): LiveData<List<Coordinates>>

    // insert coordinates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoordinates(coordinates: Coordinates): Long

    // delete all coordinates
    @Query("DELETE FROM coordinates")
    fun deleteAllCoordinates()

    // delete route of a single record
    @Query("DELETE FROM coordinates where runningId = :id")
    fun deleteRelatedCoordinates(id: Long)

}

@Dao
interface UserDao {
    @Query("select * from user")
    fun getUser(): LiveData<User>

    @Query("SELECT weight FROM user")
    fun getUserWeight(): Int

    @Query("SELECT (SELECT COUNT(*) FROM user) == 0")
    fun checkNewUser(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    //Delete is not an option in our app
//    @Delete
//    suspend fun delete(user: User)
}