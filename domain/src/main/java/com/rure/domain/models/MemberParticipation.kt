package com.rure.domain.models

import com.rure.domain.entities.Member
import java.time.LocalDate


data class MemberParticipation(
    val date: LocalDate,
    val memberId: Int,
    val attendanceStatus: AttendanceState,
    var member: Member
) {

}
