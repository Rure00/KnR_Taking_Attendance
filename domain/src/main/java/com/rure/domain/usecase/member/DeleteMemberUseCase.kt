package com.rure.domain.usecase.member

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import com.rure.knr_takingattendance.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationByMemberUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(
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