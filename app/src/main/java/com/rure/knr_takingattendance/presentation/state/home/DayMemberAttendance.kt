package com.rure.knr_takingattendance.presentation.state.home

import java.time.LocalDate

data class DayMemberAttendance(
    val name: String,
    val localDate: LocalDate,
    val attendanceStatus: AttendanceState
)
