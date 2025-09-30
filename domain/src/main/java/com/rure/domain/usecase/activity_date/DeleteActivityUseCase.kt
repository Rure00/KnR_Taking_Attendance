package com.rure.domain.usecase.activity_date

import com.rure.domain.models.ActivityDateDto
import com.rure.domain.repository.ActivityDateRepository
import com.rure.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.domain.usecase.participation.GetParticipationWhenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteActivityUseCase(
    private val activityDateRepository: ActivityDateRepository,
    private val getParticipationWhenUseCase: GetParticipationWhenUseCase,
    private val deleteMemberParticipationUseCase: DeleteMemberParticipationUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(activity: ActivityDateDto) = withContext(ioDispatcher) {
        activityDateRepository.delete(activity)

        getParticipationWhenUseCase.invoke(activity.date).forEach {
            deleteMemberParticipationUseCase.invoke(it)
        }
    }
}