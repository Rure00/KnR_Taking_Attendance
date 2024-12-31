package com.rure.knr_takingattendance.presentation.state.home

import java.time.LocalDate

data class DayAttendance(
    val day: LocalDate,
    val attendance: Map<AttendanceState, Int>
)
