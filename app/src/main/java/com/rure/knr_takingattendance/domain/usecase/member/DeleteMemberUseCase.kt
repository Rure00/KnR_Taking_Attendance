package com.rure.knr_takingattendance.domain.usecase.member

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        member: Member
    ) = withContext(ioDispatcher) {
        memberRepository.deleteMember(member)
    }
}