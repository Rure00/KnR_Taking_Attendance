package com.rure.domain.usecase.activity_date

import com.rure.knr_takingattendance.domain.repository.ActivityDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class GetActivitiesFromUseCase @Inject constructor(
    private val activityDateRepository: ActivityDateRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(date: LocalDate) = withContext(ioDispatcher) {
        return@withContext activityDateRepository.getActivitiesFrom(date)
    }
}