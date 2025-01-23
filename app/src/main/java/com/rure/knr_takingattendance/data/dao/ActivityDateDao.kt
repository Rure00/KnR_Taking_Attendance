package com.rure.knr_takingattendance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rure.knr_takingattendance.data.entities.ActivityDate
import com.rure.knr_takingattendance.data.entities.Member

@Dao
interface ActivityDateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActivity(activityDate: ActivityDate)

    @Query("SELECT * FROM activity_date WHERE date > :givenDate")
    fun getAllMembers(givenDate: String): List<ActivityDate>
}