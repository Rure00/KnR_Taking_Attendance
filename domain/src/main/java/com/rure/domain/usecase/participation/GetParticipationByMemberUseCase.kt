package com.rure.domain.usecase.participation

import com.rure.domain.entities.Member
import com.rure.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
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