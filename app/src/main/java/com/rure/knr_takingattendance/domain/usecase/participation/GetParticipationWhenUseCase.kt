package com.rure.knr_takingattendance.domain.usecase.participation

import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class GetParticipationWhenUseCase @Inject constructor(
    private val participationRepository: MemberParticipationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(date: LocalDate): List<MemberParticipation> = withContext(ioDispatcher) {
        return@withContext participationRepository.getMemberParticipationWhen(date)
    }
}