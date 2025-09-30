package com.rure.data.repository

import android.util.Log
import com.rure.data.dao.MemberDao
import com.rure.data.entities.Member
import com.rure.data.entities.toDto
import com.rure.data.entities.toEntity
import com.rure.domain.models.MemberDto
import com.rure.domain.models.Position
import com.rure.domain.repository.MemberRepository
import kotlinx.coroutines.flow.collectLatest
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
    ): MemberDto? {
        return kotlin.runCatching {
            val id = memberDao.insertMember(
                Member(name, birth, position, joinDate, phoneNumber)
            )

            Log.d(tag, "member created...")

            memberDao.getMemberById(id.toInt())?.toDto()
        }.onFailure {
            Log.e(tag, "insertMember Fail: ${it.message}")
        }.getOrNull()
    }

    override suspend fun deleteMember(member: MemberDto) {
        kotlin.runCatching {
            memberDao.deleteMember(member.toEntity())
        }
    }

    override suspend fun updateMember(member: MemberDto) {
        kotlin.runCatching {
            memberDao.insertMember(member.toEntity())
        }
    }

    override suspend fun getAllMembers(): List<MemberDto> {
        return kotlin.runCatching {
            memberDao.getAllMembers().map {
                it.toDto()
            }
        }.getOrDefault(listOf())
    }

    override suspend fun getMemberById(id: Int): MemberDto? {
        return kotlin.runCatching {
            memberDao.getMemberById(id)?.toDto()
        }.getOrNull()
    }

    override fun subscribeMemberFlow() = flow {
        memberDao.subscribeMemberFlow().collectLatest { entities ->
            emit(entities.map { it.toDto() })
        }
    }

}