package com.rure.knr_takingattendance.domain.usecase.participation

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.presentation.state.detail.AttendanceHistory
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAttendanceHistoryUseCase @Inject constructor(
    private val getParticipationByMemberUseCase: GetParticipationByMemberUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        member: Member
    ) = withContext(ioDispatcher) {
        val list = getParticipationByMemberUseCase.invoke(member)
        if(list.isEmpty()) return@withContext null

        var total = 0
        var attendNum = 0
        var nonAttendNum = 0
        var lateNum = 0
        var forcibleNum = 0

        val yearly = mutableListOf<Map<Int, AttendanceState>>()
        val monthly = mutableMapOf<Int, AttendanceState>()

        var year = list.first().date.year
        list.forEach {
            if(year != it.date.year) {
                yearly.add(monthly)
                monthly.clear()
                year = it.date.year
            }
            monthly[it.date.monthValue] = it.attendanceStatus

            total++
            when(it.attendanceStatus) {
                AttendanceState.All -> null
                AttendanceState.Attend -> attendNum++
                AttendanceState.NonAttend -> nonAttendNum++
                AttendanceState.Tardy -> lateNum++
                AttendanceState.Absence -> forcibleNum++
            }
        }

        return@withContext AttendanceHistory(
            member = member,
            attendanceRate = (attendNum) / total,
            total = total,
            attendNum = attendNum,
            nonAttendNum = nonAttendNum,
            lateNum = lateNum,
            forcibleNum = forcibleNum,
            yearlyAttendance = listOf()
        )
    }
}