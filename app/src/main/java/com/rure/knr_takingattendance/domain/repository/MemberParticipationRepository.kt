package com.rure.knr_takingattendance.domain.repository

import com.rure.knr_takingattendance.data.entities.MemberParticipation
import java.time.LocalDate

interface MemberParticipationRepository {
    fun insertMemberParticipation(memberParticipation: MemberParticipation)
    fun updateMemberParticipation(memberParticipation: MemberParticipation)
    fun deleteMemberParticipation(memberParticipation: MemberParticipation)

    fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation>
    fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation>
}