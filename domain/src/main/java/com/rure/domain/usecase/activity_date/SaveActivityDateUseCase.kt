package com.rure.domain.usecase.activity_date

import com.rure.domain.entities.ActivityDate
import com.rure.domain.repository.ActivityDateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveActivityDateUseCase(
    private val activityDateRepository: ActivityDateRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(activityDate: ActivityDate) = withContext(ioDispatcher) {
        activityDateRepository.insert(activityDate)
    }
}