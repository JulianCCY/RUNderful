package com.example.running_app.data.db

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
    fun getLatestSteps(): LiveData<Int>

    // get two latest steps
    @Query("SELECT totalStep FROM running ORDER BY rid DESC LIMIT 2")
    fun getLatestTwoSteps(): LiveData<List<Int>>

    // get average steps
    @Query("SELECT ROUND(AVG(totalStep)) FROM running")
    fun getAverageSteps(): LiveData<Int>

    // get latest distance
    @Query("SELECT ROUND(distance, 2) FROM running ORDER BY rid DESC LIMIT 1")
    fun getLatestDistance(): LiveData<Double>

    // get average distance
    @Query("SELECT ROUND(AVG(distance), 2) from running")
    fun getAverageDistance(): LiveData<Double>

    // get latest speed
    @Query("SELECT ROUND(avgSpeed, 2) FROM running ORDER BY rid DESC LIMIT 1")
    fun getLatestSpeed(): LiveData<Double>

    // get two latest speed
    @Query("SELECT ROUND(avgSpeed, 2) FROM running ORDER BY rid DESC LIMIT 2")
    fun getLatestTwoSpeed(): LiveData<List<Double>>

    // get average speed
    @Query("SELECT ROUND(AVG(avgSpeed), 2) from running")
    fun getAverageSpeed(): LiveData<Double>

    // get latest and second last to compare
    @Query("SELECT * FROM running ORDER BY rid DESC LIMIT 2")
    fun getLatestAndPreviousRunningRecord(): LiveData<List<Running>>

    // get latest and second sum of running calories to compare
    @Query("SELECT SUM(calories) FROM running GROUP BY date ORDER BY rid DESC LIMIT 2")
    fun getLatestAndPreviousCalories(): LiveData<List<Int>>

    // get all the duration
    @Query("SELECT duration FROM running")
    fun getTotalDuration(): LiveData<List<String>>

    // get highest stride length
    @Query("SELECT MAX(avgStrideLength) from running")
    fun getHighestStrideLength(): Double

    // delete record by rid
    @Query("DELETE FROM running WHERE rid = :id")
    fun deleteRecordByRID(id: Long)

    // delete all record
    @Query("DELETE FROM running")
    fun deleteAllRecords()

    //********************************** StatOverViewForUI **********************************

    // number of running records
    @Query("SELECT COUNT(*) FROM running")
    fun getNoOfRecords(): LiveData<Int>

    // total steps
    @Query("SELECT SUM(totalStep) FROM running")
    fun getTotalSteps(): LiveData<Int>

    // total distance user runs
    @Query("SELECT SUM(distance) FROM running")
    fun getTotalDistance(): LiveData<Double>

    // Average speed of last five records
    @Query("SELECT avgSpeed FROM running ORDER BY rid DESC LIMIT 5")
    fun getLastFiveAverageSpeed(): LiveData<List<Double>>

    // Average heart rate of last five records
    @Query("SELECT avgHR FROM running WHERE avgHR <> 0 ORDER BY rid DESC LIMIT 5")
    fun getLastFiveAverageHeartRate(): LiveData<List<Int>>

    @Query("SELECT MAX(avgSpeed) FROM running")
    fun getHighestSpeed(): Double

    @Query("SELECT SUM(calories) FROM running")
    fun get_calories_burnt(): Int

    @Query("SELECT COUNT(*) FROM running")
    fun getNumberOfRecords(): Int

    @Query("SELECT SUM(distance) FROM running")
    fun getTotaldistance(): Double

    @Query("SELECT SUM(totalStep) FROM running")
    fun getTotalStepsForGoal(): Int
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