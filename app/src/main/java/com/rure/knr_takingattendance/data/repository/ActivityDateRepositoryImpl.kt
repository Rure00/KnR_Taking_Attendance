package com.rure.knr_takingattendance.data.repository

import com.rure.knr_takingattendance.data.dao.ActivityDateDao
import com.rure.knr_takingattendance.data.entities.ActivityDate
import com.rure.knr_takingattendance.domain.repository.ActivityDateRepository
import java.time.LocalDate
import javax.inject.Inject

class ActivityDateRepositoryImpl @Inject constructor(
    private val dao: ActivityDateDao
): ActivityDateRepository {
    override suspend fun insert(activityDate: ActivityDate) {
        kotlin.runCatching {
            dao.insertActivity(activityDate)
        }
    }

    override suspend fun getActivitiesFrom(date: LocalDate): List<ActivityDate> {
        return kotlin.runCatching {
            dao.getAllActivityFromWhen(date.toString())
        }.getOrDefault(listOf())
    }
}