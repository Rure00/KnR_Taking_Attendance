package com.rure.knr_takingattendance.domain.repository

import com.rure.knr_takingattendance.data.entities.MemberParticipation
import java.time.LocalDate

interface MemberParticipationRepository {
    suspend fun insertMemberParticipation(memberParticipation: MemberParticipation)
    suspend fun updateMemberParticipation(memberParticipation: MemberParticipation)
    suspend fun deleteMemberParticipation(memberParticipation: MemberParticipation)

    suspend fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation>
    suspend fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation>
}