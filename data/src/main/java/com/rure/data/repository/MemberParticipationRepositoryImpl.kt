package com.rure.data.repository

import com.rure.data.dao.MemberDao
import com.rure.data.dao.ParticipationToMemberDao
import com.rure.domain.models.MemberParticipation
import com.rure.data.entities.ParticipationToMember
import com.rure.data.entities.toDto
import com.rure.domain.repository.MemberParticipationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


class MemberParticipationRepositoryImpl @Inject constructor(
    private val participationDao: ParticipationToMemberDao,
    private val memberDao: MemberDao,
    private val ioDispatcher: CoroutineDispatcher
): MemberParticipationRepository {
    override suspend fun insertMemberParticipation(memberParticipation: MemberParticipation) {
        kotlin.runCatching {
            val list = participationDao.getMemberParticipationWhen(memberParticipation.date)

            if(list.any { it.key.id == memberParticipation.memberId }) {
                participationDao.updateMemberParticipation(
                    ParticipationToMember(
                        date = memberParticipation.date,
                        memberId = memberParticipation.memberId,
                        attendanceStatus = memberParticipation.attendanceStatus
                    )
                )
            } else {
                participationDao.insertMemberParticipation(
                    ParticipationToMember(
                        date = memberParticipation.date,
                        memberId = memberParticipation.memberId,
                        attendanceStatus = memberParticipation.attendanceStatus
                    )
                )
            }
        }
    }

    override suspend fun deleteMemberParticipation(memberParticipation: MemberParticipation) {
        kotlin.runCatching {
            participationDao.deleteMemberParticipation(
                ParticipationToMember(
                    date = memberParticipation.date,
                    memberId = memberParticipation.memberId,
                    attendanceStatus = memberParticipation.attendanceStatus
                )
            )
        }
    }

    override suspend fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation> {
        return kotlin.runCatching {
//            val list = participationDao.getMemberParticipationWhen(date)
//
//            val str = list.map {
//                "(${it.memberId}, ${it.member.name})"
//            }
//            Log.d("MemberParticipationRepositoryImpl", "${date.toString()}: ${str}")
//
//            list

            val map = participationDao.getMemberParticipationWhen(date)
            val list = mutableListOf<MemberParticipation>()

            withContext(ioDispatcher) {
                val getMemberByIdTask = map.map {
                    async {
                        list.add(
                            MemberParticipation(
                                date = date,
                                memberId = it.key.id,
                                attendanceStatus = it.value.attendanceStatus,
                                member =  memberDao.getMemberById(it.key.id)!!.toDto()
                            )
                        )
                    }
                }

                getMemberByIdTask.awaitAll()
            }

            list
        }.getOrDefault(listOf())
    }

    override suspend fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation> {
        return kotlin.runCatching {
            val map = participationDao.getMemberParticipationByMember(memberId)
            val list = mutableListOf<MemberParticipation>()

            withContext(ioDispatcher) {
                val getMemberByIdTask = map.map {
                    async {
                        list.add(
                            MemberParticipation(
                                date = it.date,
                                memberId = it.memberId,
                                attendanceStatus = it.attendanceStatus,
                                member =  memberDao.getMemberById(it.memberId)!!.toDto()
                            )
                        )
                    }
                }

                getMemberByIdTask.awaitAll()
            }

            list


        }.getOrDefault(listOf())
    }

}