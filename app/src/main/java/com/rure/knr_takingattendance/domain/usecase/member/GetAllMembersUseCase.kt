package com.rure.knr_takingattendance.domain.usecase.member

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class GetAllMembersUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Member> = withContext(ioDispatcher) {
        return@withContext memberRepository.getAllMembers()
    }
}