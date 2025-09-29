package com.rure.knr_takingattendance.domain.usecase.models

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import java.time.LocalDate


data class MemberParticipation(
    val date: LocalDate,
    val memberId: Int,
    val attendanceStatus: AttendanceState,
    var member: Member
) {

}
