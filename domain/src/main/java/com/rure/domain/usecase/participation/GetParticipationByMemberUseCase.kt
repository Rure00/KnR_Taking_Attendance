package com.rure.domain.usecase.participation

import com.rure.domain.models.MemberDto
import com.rure.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetParticipationByMemberUseCase(
    private val participationRepository: MemberParticipationRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(memberDto: MemberDto) = withContext(ioDispatcher) {
        return@withContext participationRepository.getMemberParticipationByMember(memberDto.id)
    }
}