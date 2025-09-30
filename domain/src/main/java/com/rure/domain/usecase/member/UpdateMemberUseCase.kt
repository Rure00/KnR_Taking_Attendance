package com.rure.domain.usecase.member

import com.rure.domain.models.MemberDto
import com.rure.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateMemberUseCase(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        memberDto: MemberDto
    ) = withContext(ioDispatcher) {
        memberRepository.updateMember(memberDto)
    }
}