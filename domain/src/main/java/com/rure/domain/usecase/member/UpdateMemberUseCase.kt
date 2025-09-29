package com.rure.domain.usecase.member

import com.rure.domain.entities.Member
import com.rure.domain.entities.Position
import com.rure.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UpdateMemberUseCase(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        member: Member
    ) = withContext(ioDispatcher) {
        memberRepository.updateMember(member)
    }
}