package com.rure.knr_takingattendance.data.repository

import com.rure.knr_takingattendance.data.dao.MemberParticipationDao
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import java.time.LocalDate
import javax.inject.Inject

// TODO("Not yet implemented")
class MemberParticipationRepositoryImpl @Inject constructor(
    private val participationDao: MemberParticipationDao
): MemberParticipationRepository {
    override fun insertMemberParticipation(memberParticipation: MemberParticipation) {
    }

    override fun updateMemberParticipation(memberParticipation: MemberParticipation) {
    }

    override fun deleteMemberParticipation(memberParticipation: MemberParticipation) {
    }

    override fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation> {
        return listOf()
    }

    override fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation> {
        return listOf()
    }

}