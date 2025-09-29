package com.rure.presentation.intent

import com.rure.knr_takingattendance.domain.usecase.models.MemberParticipation
import java.time.LocalDate

sealed class ParticipationIntent {
    data object CreateActivityDate: ParticipationIntent()
    data object DeleteActivityDate: ParticipationIntent()

    data class SaveParticipation(val dayMemberAttendance: MemberParticipation): ParticipationIntent()
    data class DeleteParticipation(val dayMemberAttendance: MemberParticipation): ParticipationIntent()
    data class GetParticipationWhen(val date: LocalDate): ParticipationIntent()
}