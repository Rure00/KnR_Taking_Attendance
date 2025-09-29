package com.rure.domain.repository

import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MemberRepository {
    suspend fun insertMember(
        name: String,
        birth: LocalDate,
        position: Map<Position, Boolean>,
        joinDate: LocalDate,
        phoneNumber: String,
    ): Member?
    suspend fun deleteMember(member: Member)
    suspend fun updateMember(member: Member)

    suspend fun getAllMembers(): List<Member>
    suspend fun getMemberById(id: Int): Member?

    fun subscribeMemberFlow(): Flow<MemberFlowResult>

}