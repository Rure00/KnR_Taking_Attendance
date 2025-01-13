package com.rure.knr_takingattendance.data.repository

import com.rure.knr_takingattendance.data.dao.MemberDao
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    override fun subscribeMemberFlow() = flow {
        emit(MemberFlowResult.Loading)
        memberDao.subscribeMemberFlow().collect {
            emit(MemberFlowResult.Success(it))
        }
    }.catch { e ->
        emit(MemberFlowResult.Fail(e))
    }

}