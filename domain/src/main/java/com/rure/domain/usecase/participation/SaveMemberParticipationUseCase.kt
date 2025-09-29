package com.rure.domain.usecase.participation

import com.rure.knr_takingattendance.domain.usecase.models.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveMemberParticipationUseCase @Inject constructor(
    private val participationRepository: MemberParticipationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        participation: MemberParticipation
    ) = withContext(ioDispatcher) {
        participationRepository.insertMemberParticipation(participation)
    }
}