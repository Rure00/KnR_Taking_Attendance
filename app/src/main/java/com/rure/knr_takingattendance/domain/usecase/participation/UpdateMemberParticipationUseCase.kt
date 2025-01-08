package com.rure.knr_takingattendance.domain.usecase.participation

import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMemberParticipationUseCase @Inject constructor(
    private val participationRepository: MemberParticipationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        participation: MemberParticipation
    ) = withContext(ioDispatcher) {
        participationRepository.updateMemberParticipation(participation)
    }
}