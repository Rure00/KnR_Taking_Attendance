package com.rure.knr_takingattendance.data.repository

import com.rure.knr_takingattendance.data.dao.MemberDao
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import javax.inject.Inject

// TODO("Not yet implemented")
class MemberRepositoryImpl @Inject constructor(
    private val memberDao: MemberDao
): MemberRepository {
    override suspend fun insertMember(member: Member) {
        kotlin.runCatching {
            memberDao.insertMember(member)
        }
    }

    override suspend fun deleteMember(member: Member) {
        kotlin.runCatching {
            memberDao.deleteMember(member)
        }
    }

    override suspend fun updateMember(member: Member) {
        kotlin.runCatching {
            memberDao.updateMember(member)
        }
    }

    override suspend fun getAllMembers(): List<Member> {
        return kotlin.runCatching {
            memberDao.getAllMembers()
        }.getOrDefault(listOf())
    }

    override suspend fun getMemberById(id: Int): Member? {
        return kotlin.runCatching {
            memberDao.getMemberById(id)
        }.getOrNull()
    }

}