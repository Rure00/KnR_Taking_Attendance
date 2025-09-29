package com.rure.domain.usecase.activity_date

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class SubscribeMemberFlowUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = memberRepository.subscribeMemberFlow()

}