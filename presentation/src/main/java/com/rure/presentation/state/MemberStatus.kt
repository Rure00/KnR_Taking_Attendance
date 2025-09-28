package com.rure.knr_takingattendance.presentation.state

import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import java.time.LocalDate

data class MemberStatus(
    val name: String,
    val attendStatus: Map<LocalDate, AttendanceState>,
    val position: String,
)
