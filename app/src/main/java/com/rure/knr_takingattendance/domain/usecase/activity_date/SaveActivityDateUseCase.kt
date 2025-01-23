package com.rure.knr_takingattendance.domain.usecase.activity_date

import com.rure.knr_takingattendance.data.entities.ActivityDate
import com.rure.knr_takingattendance.domain.repository.ActivityDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveActivityDateUseCase @Inject constructor(
    private val activityDateRepository: ActivityDateRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(activityDate: ActivityDate) = withContext(ioDispatcher) {
        activityDateRepository.insert(activityDate)
    }
}