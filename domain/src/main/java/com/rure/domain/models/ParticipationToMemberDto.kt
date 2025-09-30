package com.rure.domain.models

import java.time.LocalDate

data class ParticipationToMemberDto(
    val date: LocalDate,
    val memberId: Int,
    val attendanceStatus: AttendanceState,
)
