package com.rure.domain.usecase.participation

import com.rure.domain.models.MemberParticipation
import com.rure.domain.repository.MemberRepository
import com.rure.domain.models.AttendanceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.time.LocalDate

class InitDayAttendanceUseCase(
    private val repository: MemberRepository,
    private val saveMemberParticipationUseCase: SaveMemberParticipationUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(date: LocalDate): List<MemberParticipation> = withContext(ioDispatcher) {
        val task = mutableListOf<Deferred<Unit>>()
        val memberParticipationList = repository.getAllMembers().map {
            MemberParticipation(
                date = date,
                memberId = it.id,
                attendanceStatus = AttendanceState.NonAttend,
                member = it
            ).also {
                task.add(
                    async { saveMemberParticipationUseCase.invoke(it) }
                )
            }
        }

        task.awaitAll()

        return@withContext memberParticipationList
    }
}