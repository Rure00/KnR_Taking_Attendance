package com.rure.presentation.state.detail

import com.rure.data.entities.Member
import com.rure.domain.models.AttendanceState
import java.time.LocalDate

data class AttendanceHistory(
    val member: Member,

    val attendanceRate: Int,
    val total: Int,
    val attendNum: Int,
    val nonAttendNum: Int,
    val lateNum: Int,
    val forcibleNum: Int,

    val yearlyAttendance: List<YearlyAttendance>
)

data class DailyAttendance(
    val date: LocalDate,
    val attendance: AttendanceState
)


data class YearlyAttendance(
    val year: Int,
    val monthlyAttendances: Map<Int, List<DailyAttendance>>
)
