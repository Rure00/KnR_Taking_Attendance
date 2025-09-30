package com.rure.domain.usecase.member

import com.rure.domain.models.MemberDto
import com.rure.domain.repository.MemberRepository
import com.rure.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.domain.usecase.participation.GetParticipationByMemberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class DeleteMemberUseCase(
    private val memberRepository: MemberRepository,
    private val getParticipationByMemberUseCase: GetParticipationByMemberUseCase,
    private val deleteMemberParticipationUseCase: DeleteMemberParticipationUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        memberDto: MemberDto
    ) = withContext(ioDispatcher) {
        memberRepository.deleteMember(memberDto)

        getParticipationByMemberUseCase.invoke(memberDto).map {
            async { deleteMemberParticipationUseCase.invoke(it) }
        }.awaitAll()
    }
}