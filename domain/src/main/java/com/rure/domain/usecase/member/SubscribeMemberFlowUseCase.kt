package com.rure.domain.usecase.member

import com.rure.domain.models.MemberDto
import com.rure.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class SubscribeMemberFlowUseCase(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = memberRepository.subscribeMemberFlow()
            .map { Result.success(it) }
            .catch { e -> emit(Result.failure(e)) }
            .flowOn(ioDispatcher)
}