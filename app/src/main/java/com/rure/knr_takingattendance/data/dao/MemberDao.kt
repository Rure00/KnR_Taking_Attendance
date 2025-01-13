package com.rure.knr_takingattendance.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MemberDao {
    @Insert
    fun insertMember(member: Member)
    @Update
    fun updateMember(member: Member)
    @Delete
    fun deleteMember(member: Member)

    @Query("SELECT * FROM members WHERE id == :id")
    fun getMemberById(id: Int): Member?

    @Query("SELECT * FROM members")
    fun getAllMembers(): List<Member>

    @Query("SELECT * FROM members")
    fun subscribeMemberFlow(): Flow<List<Member>>

}