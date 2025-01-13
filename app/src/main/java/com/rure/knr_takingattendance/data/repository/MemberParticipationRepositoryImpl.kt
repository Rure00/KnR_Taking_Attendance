package com.rure.knr_takingattendance.data.repository

import com.rure.knr_takingattendance.data.dao.MemberParticipationDao
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import java.time.LocalDate
import javax.inject.Inject


class MemberParticipationRepositoryImpl @Inject constructor(
    private val participationDao: MemberParticipationDao
): MemberParticipationRepository {
    override suspend fun insertMemberParticipation(memberParticipation: MemberParticipation) {
        kotlin.runCatching {
            val list = participationDao.getMemberParticipationWhen(memberParticipation.date)

            if(list.any { it.memberId == memberParticipation.memberId }) {
                participationDao.updateMemberParticipation(memberParticipation)
            } else {
                participationDao.insertMemberParticipation(memberParticipation)
            }
        }
    }

    override suspend fun deleteMemberParticipation(memberParticipation: MemberParticipation) {
        kotlin.runCatching {
            participationDao.deleteMemberParticipation(memberParticipation)
        }
    }

    override suspend fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation> {
        return kotlin.runCatching {
            participationDao.getMemberParticipationWhen(date)
        }.getOrDefault(listOf())
    }

    override suspend fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation> {
        return kotlin.runCatching {
            participationDao.getMemberParticipationByMember(memberId)
        }.getOrDefault(listOf())
    }

}