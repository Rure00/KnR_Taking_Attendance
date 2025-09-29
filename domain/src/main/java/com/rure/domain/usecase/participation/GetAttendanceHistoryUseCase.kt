package com.rure.domain.usecase.participation

import com.rure.domain.entities.Member
import com.rure.domain.models.AttendanceHistory
import com.rure.domain.models.AttendanceState
import com.rure.domain.models.DailyAttendance
import com.rure.domain.models.YearlyAttendance
import com.rure.domain.usecase.activity_date.GetAllActivitiesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetAttendanceHistoryUseCase(
    private val getParticipationByMemberUseCase: GetParticipationByMemberUseCase,
    private val getAllActivitiesUseCase: GetAllActivitiesUseCase,
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
        getAllActivitiesUseCase.invoke().forEach {
            yearObject[it.date.year] = mutableMapOf(it.date.monthValue to mutableListOf())
        }
        list.forEach {
            val year = it.date.year
            val month = it.date.monthValue

            //yearObject.getOrPut(year) { mutableMapOf(month to mutableListOf(DailyAttendance(it.date, it.attendanceStatus)) )}
            yearObject[year]!![month]!!.add(DailyAttendance(it.date, it.attendanceStatus))

            total++
            when(it.attendanceStatus) {
                AttendanceState.All -> null
                AttendanceState.Attend -> attendNum++
                AttendanceState.NonAttend -> nonAttendNum++
                AttendanceState.Tardy -> lateNum++
                AttendanceState.Absence -> forcibleNum++
            }
        }

        val rate = ((attendNum.toDouble()) / total.toDouble() * 100).toInt()

        //Log.d(tag, "rate: $rate, total: $total, attend: $attendNum, non: $nonAttendNum, late: $lateNum, absence: $forcibleNum")

        val yearlyAttendanceList = mutableListOf<YearlyAttendance>()
        yearObject.keys.sortedDescending().forEach {
            yearlyAttendanceList.add(
                YearlyAttendance(
                    year = it, monthlyAttendances = yearObject[it]!!
                )
            )
        }

//        yearlyAttendanceList.forEach {
//            Log.d(tag, "year: ${it.year}")
//            it.monthlyAttendances.forEach { month ->
//                Log.d(tag, "month(${month.key}): ${month.value.map { day -> "${day.date}: ${day.attendance}" }}")
//            }
//        }

        return@withContext AttendanceHistory(
            member = member,
            attendanceRate = rate,
            total = total,
            attendNum = attendNum,
            nonAttendNum = nonAttendNum,
            lateNum = lateNum,
            forcibleNum = forcibleNum,
            yearlyAttendance = yearlyAttendanceList
        )
    }
}