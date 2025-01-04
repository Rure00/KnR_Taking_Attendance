package com.rure.knr_takingattendance.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import java.time.LocalDate

interface MemberDao {
    @Insert
    fun insertMember(member: Member)
    @Update
    fun updateMember(member: Member)
    @Delete
    fun deleteMember(member: Member)

    @Query("SELECT * FROM members WHERE id == :id")
    fun getMemberById(id: Int): List<MemberParticipation>
}