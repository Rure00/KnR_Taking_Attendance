package com.rure.knr_takingattendance.domain.usecase.participation

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetParticipationByMemberUseCase @Inject constructor(
    private val participationRepository: MemberParticipationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(member: Member) = withContext(ioDispatcher) {
        return@withContext participationRepository.getMemberParticipationByMember(member.id)
    }
}