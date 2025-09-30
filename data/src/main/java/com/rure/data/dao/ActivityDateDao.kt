package com.rure.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rure.domain.models.ActivityDateDto

@Dao
interface ActivityDateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActivity(activityDate: ActivityDateDto)

    @Delete
    fun deleteActivity(activityDate: ActivityDateDto)

    @Query("SELECT * FROM activity_date")
    fun getAll(): List<ActivityDateDto>

    @Query("SELECT * FROM activity_date WHERE date >= :givenDate")
    fun getAllActivityFromWhen(givenDate: String): List<ActivityDateDto>
}