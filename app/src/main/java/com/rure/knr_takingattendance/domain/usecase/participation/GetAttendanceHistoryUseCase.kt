package com.rure.knr_takingattendance.domain.usecase.participation

import android.util.Log
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.presentation.state.detail.AttendanceHistory
import com.rure.knr_takingattendance.presentation.state.detail.DailyAttendance
import com.rure.knr_takingattendance.presentation.state.detail.YearlyAttendance
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
        val tag = "GetAttendanceHistoryUseCase"

        val list = getParticipationByMemberUseCase.invoke(member)
        if(list.isEmpty()) return@withContext null

        var total = 0
        var attendNum = 0
        var nonAttendNum = 0
        var lateNum = 0
        var forcibleNum = 0

        val yearObject = mutableMapOf<Int, MutableMap<Int, MutableList<DailyAttendance>>>()
        list.forEach {
            val year = it.date.year
            val month = it.date.monthValue

            yearObject.getOrPut(year) { mutableMapOf(month to mutableListOf(DailyAttendance(it.date, it.attendanceStatus)) )}

            total++
            when(it.attendanceStatus) {
                AttendanceState.All -> null
                AttendanceState.Attend -> attendNum++
                AttendanceState.NonAttend -> nonAttendNum++
                AttendanceState.Tardy -> lateNum++
                AttendanceState.Absence -> forcibleNum++
            }
        }

        val yearlyAttendanceList = mutableListOf<YearlyAttendance>()
        yearObject.keys.sortedDescending().forEach {
            yearlyAttendanceList.add(
                YearlyAttendance(
                    year = it, monthlyAttendances = yearObject[it]!!
                )
            )
        }

        Log.d(tag, "yearlyAttendanceList size: ${yearlyAttendanceList.size}")

        return@withContext AttendanceHistory(
            member = member,
            attendanceRate = (attendNum) / total * 100,
            total = total,
            attendNum = attendNum,
            nonAttendNum = nonAttendNum,
            lateNum = lateNum,
            forcibleNum = forcibleNum,
            yearlyAttendance = yearlyAttendanceList
        )
    }
}