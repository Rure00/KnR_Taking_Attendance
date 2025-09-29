package com.rure.domain.models

import java.time.LocalDate

data class MemberStatus(
    val name: String,
    val attendStatus: Map<LocalDate, AttendanceState>,
    val position: String,
)
