package com.rure.domain.usecase.member

import com.rure.domain.models.MemberDto
import com.rure.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetAllMembersUseCase(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<MemberDto> = withContext(ioDispatcher) {
        return@withContext memberRepository.getAllMembers()
    }
}