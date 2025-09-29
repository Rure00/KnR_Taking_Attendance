package com.rure.domain.usecase.member

import com.rure.domain.entities.Member
import com.rure.domain.repository.MemberRepository
import com.rure.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.domain.usecase.participation.GetParticipationByMemberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
        member: Member
    ) = withContext(ioDispatcher) {
        memberRepository.deleteMember(member)

        getParticipationByMemberUseCase.invoke(member).map {
            async { deleteMemberParticipationUseCase.invoke(it) }
        }.awaitAll()
    }
}