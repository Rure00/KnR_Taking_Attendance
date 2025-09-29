package com.rure.domain.models

import com.rure.presentation.state.home.AttendanceState
import java.time.LocalDate

data class MemberStatus(
    val name: String,
    val attendStatus: Map<LocalDate, AttendanceState>,
    val position: String,
)
