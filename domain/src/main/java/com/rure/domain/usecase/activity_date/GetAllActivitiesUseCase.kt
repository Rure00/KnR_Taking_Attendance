package com.rure.domain.usecase.activity_date

import com.rure.knr_takingattendance.domain.repository.ActivityDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class GetAllActivitiesUseCase @Inject constructor(
    private val activityDateRepository: ActivityDateRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        return@withContext activityDateRepository.getAllActivities()
    }
}