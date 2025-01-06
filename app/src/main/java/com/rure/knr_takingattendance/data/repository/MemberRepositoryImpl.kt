package com.rure.knr_takingattendance.data.repository

import com.rure.knr_takingattendance.data.dao.MemberDao
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import javax.inject.Inject

// TODO("Not yet implemented")
class MemberRepositoryImpl @Inject constructor(
    private val memberDao: MemberDao
): MemberRepository {
    override fun insertMember(member: Member) {

    }

    override fun deleteMember(member: Member) {

    }

    override fun updateMember(member: Member) {

    }

    override fun getAllMembers(): List<Member> {
        return listOf()
    }

    override fun getMemberById(id: Int): Member? {
        return null
    }

}