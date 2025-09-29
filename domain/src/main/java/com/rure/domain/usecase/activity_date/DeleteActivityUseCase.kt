package com.rure.domain.usecase.activity_date

import com.rure.knr_takingattendance.data.entities.ActivityDate
import com.rure.knr_takingattendance.domain.repository.ActivityDateRepository
import com.rure.knr_takingattendance.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationWhenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteActivityUseCase @Inject constructor(
    private val activityDateRepository: ActivityDateRepository,
    private val getParticipationWhenUseCase: GetParticipationWhenUseCase,
    private val deleteMemberParticipationUseCase: DeleteMemberParticipationUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(activity: ActivityDate) = withContext(ioDispatcher) {
        activityDateRepository.delete(activity)

        getParticipationWhenUseCase.invoke(activity.date).forEach {
            deleteMemberParticipationUseCase.invoke(it)
        }
    }
}