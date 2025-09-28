package com.rure.knr_takingattendance.data.repository

import android.util.Log
import com.rure.knr_takingattendance.data.dao.MemberDao
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject


class MemberRepositoryImpl @Inject constructor(
    private val memberDao: MemberDao
): MemberRepository {
    private val tag = "MemberRepositoryImpl"
    override suspend fun insertMember(
        name: String,
        birth: LocalDate,
        position: Map<Position, Boolean>,
        joinDate: LocalDate,
        phoneNumber: String
    ): Member? {
        return kotlin.runCatching {
            val id = memberDao.insertMember(
                Member(name, birth, position, joinDate, phoneNumber)
            )

            Log.d(tag, "member created...")

            memberDao.getMemberById(id.toInt())
        }.onFailure {
            Log.e(tag, "insertMember Fail: ${it.message}")
        }.getOrNull()
    }

    override suspend fun deleteMember(member: Member) {
        kotlin.runCatching {
            memberDao.deleteMember(member)
        }
    }

    override suspend fun updateMember(member: Member) {
        kotlin.runCatching {
            memberDao.insertMember(member)
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