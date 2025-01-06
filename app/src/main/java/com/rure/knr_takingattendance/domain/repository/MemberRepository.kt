package com.rure.knr_takingattendance.domain.repository

import com.rure.knr_takingattendance.data.entities.Member

interface MemberRepository {
    fun insertMember(member: Member)
    fun deleteMember(member: Member)
    fun updateMember(member: Member)

    fun getAllMembers(): List<Member>
    fun getMemberById(id: Int): Member?
}