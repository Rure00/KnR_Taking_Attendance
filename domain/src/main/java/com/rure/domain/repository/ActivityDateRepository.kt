package com.rure.domain.repository

import com.rure.domain.models.ActivityDateDto
import java.time.LocalDate

interface ActivityDateRepository {
    suspend fun insert(activityDate: ActivityDateDto)
    suspend fun delete(activityDate: ActivityDateDto)
    suspend fun getActivitiesFrom(date: LocalDate): List<ActivityDateDto>
    suspend fun getAllActivities(): List<ActivityDateDto>
}