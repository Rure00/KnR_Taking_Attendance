package com.rure.knr_takingattendance.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import java.time.LocalDate

interface MemberParticipationDao {
    @Insert
    fun insertMemberParticipation(memberParticipation: MemberParticipation)
    @Update
    fun updateMemberParticipation(memberParticipation: MemberParticipation)
    @Delete
    fun deleteMemberParticipation(memberParticipation: MemberParticipation)

    @Query("SELECT * FROM member_participation WHERE date == :date")
    fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation>

    @Query("SELECT * FROM member_participation WHERE member == :memberId")
    fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation>

}