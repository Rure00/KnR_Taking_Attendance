package com.rure.domain.models

import java.time.LocalDate

data class DayAttendanceSummaryDto(
    val date: LocalDate,
    val allNum: Int,
    val attendNum: Int,
    val nonAttendNum: Int,
    val tardyNum: Int,
    val absenceNum: Int,
)
