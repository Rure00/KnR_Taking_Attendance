package com.rure.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rure.knr_takingattendance.data.entities.ActivityDate

@Dao
interface ActivityDateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActivity(activityDate: ActivityDate)

    @Delete
    fun deleteActivity(activityDate: ActivityDate)

    @Query("SELECT * FROM activity_date")
    fun getAll(): List<ActivityDate>

    @Query("SELECT * FROM activity_date WHERE date >= :givenDate")
    fun getAllActivityFromWhen(givenDate: String): List<ActivityDate>
}