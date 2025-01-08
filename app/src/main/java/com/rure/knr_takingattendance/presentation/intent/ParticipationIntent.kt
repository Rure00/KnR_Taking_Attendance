package com.rure.knr_takingattendance.presentation.intent

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import java.time.LocalDate

sealed class ParticipationIntent {
    data class SaveParticipation(val date: LocalDate, val attendanceState: AttendanceState, val member: Member): ParticipationIntent()
    data class DeleteParticipation(val dayMemberAttendance: MemberParticipation): ParticipationIntent()
    data class UpdateParticipation(val dayMemberAttendance: MemberParticipation): ParticipationIntent()
    data class GetParticipationWhen(val date: LocalDate): ParticipationIntent()
    data class GetParticipationByMember(val member: Member): ParticipationIntent()
}