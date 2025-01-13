package com.rure.knr_takingattendance.domain.repository

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun insertMember(member: Member)
    suspend fun deleteMember(member: Member)
    suspend fun updateMember(member: Member)

    suspend fun getAllMembers(): List<Member>
    suspend fun getMemberById(id: Int): Member?

    fun subscribeMemberFlow(): Flow<MemberFlowResult>

}