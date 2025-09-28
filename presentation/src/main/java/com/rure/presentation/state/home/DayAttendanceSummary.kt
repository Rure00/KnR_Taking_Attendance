package com.rure.knr_takingattendance.presentation.state.home

import java.time.LocalDate

data class DayAttendanceSummary(
    val date: LocalDate,
    val allNum: Int,
    val attendNum: Int,
    val nonAttendNum: Int,
    val tardyNum: Int,
    val absenceNum: Int,
)
