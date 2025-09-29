package com.rure.domain.repository

import com.rure.domain.models.MemberParticipation
import java.time.LocalDate

interface MemberParticipationRepository {
    suspend fun insertMemberParticipation(memberParticipation: MemberParticipation)
    suspend fun deleteMemberParticipation(memberParticipation: MemberParticipation)

    suspend fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation>
    suspend fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation>
}