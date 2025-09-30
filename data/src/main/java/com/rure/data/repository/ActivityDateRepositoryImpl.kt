package com.rure.data.repository

import com.rure.data.dao.ActivityDateDao
import com.rure.domain.models.ActivityDateDto
import com.rure.domain.repository.ActivityDateRepository
import java.time.LocalDate
import javax.inject.Inject

class ActivityDateRepositoryImpl @Inject constructor(
    private val dao: ActivityDateDao
): ActivityDateRepository {
    override suspend fun insert(activityDate: ActivityDateDto) {
        kotlin.runCatching {
            dao.insertActivity(activityDate)
        }
    }

    override suspend fun delete(activityDate: ActivityDateDto) {
        kotlin.runCatching {
            dao.deleteActivity(activityDate)
        }
    }

    override suspend fun getActivitiesFrom(date: LocalDate): List<ActivityDateDto> {
        return kotlin.runCatching {
            dao.getAllActivityFromWhen(date.toString())
        }.getOrDefault(listOf())
    }

    override suspend fun getAllActivities(): List<ActivityDateDto> {
        return kotlin.runCatching {
            dao.getAll()
        }.getOrDefault(listOf())
    }


}