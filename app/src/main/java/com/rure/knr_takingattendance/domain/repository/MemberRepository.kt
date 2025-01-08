package com.rure.knr_takingattendance.domain.repository

import com.rure.knr_takingattendance.data.entities.Member

interface MemberRepository {
    suspend fun insertMember(member: Member)
    suspend fun deleteMember(member: Member)
    suspend fun updateMember(member: Member)

    suspend fun getAllMembers(): List<Member>
    suspend fun getMemberById(id: Int): Member?
}