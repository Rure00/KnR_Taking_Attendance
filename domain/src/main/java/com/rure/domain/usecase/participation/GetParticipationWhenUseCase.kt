package com.rure.domain.usecase.participation

import com.rure.domain.models.MemberParticipation
import com.rure.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate

class GetParticipationWhenUseCase(
    private val participationRepository: MemberParticipationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(date: LocalDate): List<MemberParticipation> = withContext(ioDispatcher) {
        return@withContext participationRepository.getMemberParticipationWhen(date)
    }
}