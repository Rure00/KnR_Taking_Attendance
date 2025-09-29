package com.rure.domain.repository

import com.rure.domain.entities.ActivityDate
import java.time.LocalDate

interface ActivityDateRepository {
    suspend fun insert(activityDate: ActivityDate)
    suspend fun delete(activityDate: ActivityDate)
    suspend fun getActivitiesFrom(date: LocalDate): List<ActivityDate>
    suspend fun getAllActivities(): List<ActivityDate>
}