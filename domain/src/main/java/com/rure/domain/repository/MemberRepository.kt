package com.rure.domain.repository

import com.rure.domain.models.Position
import com.rure.domain.models.MemberDto
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MemberRepository {
    suspend fun insertMember(
        name: String,
        birth: LocalDate,
        position: Map<Position, Boolean>,
        joinDate: LocalDate,
        phoneNumber: String,
    ): MemberDto?
    suspend fun deleteMember(member: MemberDto)
    suspend fun updateMember(member: MemberDto)

    suspend fun getAllMembers(): List<MemberDto>
    suspend fun getMemberById(id: Int): MemberDto?

    fun subscribeMemberFlow(): Flow<List<MemberDto>>

}