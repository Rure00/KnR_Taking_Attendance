package com.rure.knr_takingattendance.domain.usecase.participation

import android.util.Log
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class InitDayAttendanceUseCase @Inject constructor(
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