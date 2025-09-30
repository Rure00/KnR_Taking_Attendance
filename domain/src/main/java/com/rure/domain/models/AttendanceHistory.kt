package com.rure.domain.models

import java.time.LocalDate

data class AttendanceHistory(
    val memberDto: MemberDto,

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
