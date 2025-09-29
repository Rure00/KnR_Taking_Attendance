package com.rure.domain.usecase.activity_date

import com.rure.domain.repository.ActivityDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate

class GetAllActivitiesUseCase(
    private val activityDateRepository: ActivityDateRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        return@withContext activityDateRepository.getAllActivities()
    }
}